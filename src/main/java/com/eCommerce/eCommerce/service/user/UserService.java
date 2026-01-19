package com.eCommerce.eCommerce.service.user;

import java.util.List;

import com.eCommerce.eCommerce.dto.UserDto;
import com.eCommerce.eCommerce.model.User;
import com.eCommerce.eCommerce.request.user.CreateUserRequest;
import com.eCommerce.eCommerce.request.user.UserUpdateRequest;

public interface UserService {

	User getUserById(Long userId);

	List<User> getAllUsers();

	User createUser(CreateUserRequest request);

	User updateUser(UserUpdateRequest request, Long userId);

	void deleteUser(Long userId);

	UserDto convertUserToDto(User user);

}
