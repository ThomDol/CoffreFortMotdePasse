package com.tom.passwordSafetyBox.Mapper;

import java.util.ArrayList;

import com.tom.passwordSafetyBox.Dto.UserDto;
import com.tom.passwordSafetyBox.entity.AppUser;

public class UserMapper {
	
	public static UserDto mapToUserDto (AppUser user) {
		return new UserDto(
				user.getId(),
				user.getEmail(),
				user.getPassword());
	}
	
	public static AppUser mapToUser (UserDto userDto) {
		return new AppUser(
				userDto.getId(),
				userDto.getEmail(),
				userDto.getPassword(),
				new ArrayList<>(),
				new ArrayList<>());
	}

}
