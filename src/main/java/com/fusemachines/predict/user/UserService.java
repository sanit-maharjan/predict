package com.fusemachines.predict.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@SuppressWarnings("unchecked")
	public UserDto getUser(String userId) {
		User user = Auth0Service.getUser(userId);
		UserDto userDto = new UserDto();
		
		CustomBeanUtils.copyProperties(user, userDto);
		userDto.setRole((List<String>) user.getAppMetadata().get("roles"));
		
		return userDto;
	}
	
	public Map<String, UserDto> getUsersMap() {
		List<UserDto> users = getAllUsers();
		Map<String, UserDto> usersMap = new HashMap<>();
		
		for (UserDto user : users) {
			usersMap.put(user.getId(), user);
		}
		
		return usersMap;
	}
}
