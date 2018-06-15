package com.fusemachines.predict.user.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {
	private String id;
	private String name;
	private String email;
	private List<String> role;
}
