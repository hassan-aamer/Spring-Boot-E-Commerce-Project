package com.eCommerce.eCommerce.service.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eCommerce.eCommerce.dto.CartItemDto;
import com.eCommerce.eCommerce.dto.OrderDto;
import com.eCommerce.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.eCommerce.model.CartItem;
import com.eCommerce.eCommerce.model.Order;
import com.eCommerce.eCommerce.model.OrderItem;
import com.eCommerce.eCommerce.model.OrderStatus;
import com.eCommerce.eCommerce.model.Product;
import com.eCommerce.eCommerce.model.User;
import com.eCommerce.eCommerce.repository.cart.CartItemRepository;
import com.eCommerce.eCommerce.repository.order.OrderItemRepository;
import com.eCommerce.eCommerce.repository.order.OrderRepository;
import com.eCommerce.eCommerce.repository.product.ProductRepository;
import com.eCommerce.eCommerce.repository.user.UserRepository;
import com.eCommerce.eCommerce.request.order.CreateOrderRequest;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            CartItemRepository cartItemRepository,
                            UserRepository userRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderDto createOrder(CreateOrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found for id: " + request.getUserId()));

        List<CartItem> cartItems = cartItemRepository.findAllById(request.getCartItemIds());

        if (cartItems == null || cartItems.isEmpty()) {
            throw new ResourceNotFoundException("CartItems not found for the provided IDs.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(request.getShippingAddress());
        order.setBillingAddress(request.getBillingAddress());
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            if (product.getInventory() < cartItem.getQuantity()) {
                throw new IllegalArgumentException("Insufficient inventory for product: " + product.getName());
            }

            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        cartItemRepository.deleteAll(cartItems);

        return mapToDto(savedOrder);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for id: " + orderId));
        order.setStatus(OrderStatus.CANCELLED);
        
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            if (product != null) {
                product.setInventory(product.getInventory() + item.getQuantity());
                productRepository.save(product);
            }
        }
        
        orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for id: " + orderId));

        return mapToDto(order);
    }

    private OrderDto mapToDto(Order order) {
        List<CartItemDto> cartItemDtos = (order.getOrderItems() != null)
                ? order.getOrderItems().stream().map(item -> new CartItemDto(item.getId(), item.getProduct().getName(),
                        item.getQuantity(), item.getPrice())).collect(Collectors.toList())
                : new ArrayList<>();

        return new OrderDto(order.getId(), order.getUser().getId(), cartItemDtos, order.getShippingAddress(),
                order.getBillingAddress(), order.getStatus());
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);

        return mapToDto(updatedOrder);
    }
}

