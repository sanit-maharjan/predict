package com.fusemachines.predict.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.json.mgmt.users.User;
import com.fusemachines.predict.auth0.Auth0Service;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@GetMapping
	public List<User> getAllUsers() {
		return Auth0Service.getAllUsers();
	}
	
}
