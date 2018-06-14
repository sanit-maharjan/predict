package com.fusemachines.predict.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDto {
	private String id;
	private String name;
	private String email;
	@JsonProperty("imageUrl")
    private String picture;
}
