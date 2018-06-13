package com.fusemachines.predict.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.auth0.json.mgmt.users.User;
import com.fusemachines.predict.auth0.Auth0Service;
import com.fusemachines.predict.user.dto.UserDto;
import com.fusemachines.predict.utils.CustomBeanUtils;

@Service
public class UserService {
	
	public List<UserDto> getAllUsers() {
		List<User> users = Auth0Service.getAllUsers();
		List<UserDto> userDtos = new ArrayList<>();
		
		for (User user : users) {
			UserDto userDto = new UserDto();
			CustomBeanUtils.copyProperties(user, userDto);
			userDtos.add(userDto);
		}
		
		return userDtos;
	}
}
