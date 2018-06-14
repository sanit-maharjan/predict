package com.fusemachines.predict.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusemachines.predict.config.AuthUtils;
import com.fusemachines.predict.user.dto.UserDto;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private AuthUtils authUtils;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<UserDto> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/my")
	public UserDto getCurrentUser() {
		return userService.getUser(authUtils.getAuth0Id());
	}
	
}
