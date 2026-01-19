package com.eCommerce.eCommerce.service.user;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerce.dto.UserDto;
import com.eCommerce.eCommerce.exceptions.AlreadyExistsException;
import com.eCommerce.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.eCommerce.model.User;
import com.eCommerce.eCommerce.repository.user.UserRepository;
import com.eCommerce.eCommerce.request.user.CreateUserRequest;
import com.eCommerce.eCommerce.request.user.UserUpdateRequest;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final ModelMapper modelMapper;

	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User createUser(CreateUserRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new AlreadyExistsException("Email already exists: " + request.getEmail());
		}
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		return userRepository.save(user);
	}

	@Override
	public User updateUser(UserUpdateRequest request, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
		userRepository.delete(user);
	}

	@Override
	public UserDto convertUserToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

}
