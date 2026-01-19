package com.eCommerce.eCommerce.controller.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eCommerce.eCommerce.dto.UserDto;
import com.eCommerce.eCommerce.model.User;
import com.eCommerce.eCommerce.request.user.CreateUserRequest;
import com.eCommerce.eCommerce.request.user.UserUpdateRequest;
import com.eCommerce.eCommerce.response.ApiResponse;
import com.eCommerce.eCommerce.service.user.UserService;

@RestController
@RequestMapping("/api/users")

public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
		User user = userService.getUserById(userId);
		UserDto userDto = userService.convertUserToDto(user);
		return ResponseEntity.ok(new ApiResponse("Success", userDto));
	}

	@GetMapping
	public ResponseEntity<ApiResponse> getAllUsers() {
		List<User> users = userService.getAllUsers();
		List<UserDto> userDtos = users.stream().map(userService::convertUserToDto).collect(Collectors.toList());
		return ResponseEntity.ok(new ApiResponse("Products fetched successfully", userDtos));
	}

	@PostMapping
	public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
		User user = userService.createUser(request);
		UserDto userDto = userService.convertUserToDto(user);
		return ResponseEntity.ok(new ApiResponse("Create User Success!", userDto));
	}

	@PutMapping("/{userId}")
	public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {
		User user = userService.updateUser(request, userId);
		UserDto userDto = userService.convertUserToDto(user);
		return ResponseEntity.ok(new ApiResponse("Update User Success!", userDto));
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		return ResponseEntity.ok(new ApiResponse("Delete User Success!", null));
	}
}
