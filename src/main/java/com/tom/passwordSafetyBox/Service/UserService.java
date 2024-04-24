package com.tom.passwordSafetyBox.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.tom.passwordSafetyBox.Exception.UserAlreadyExistException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.tom.passwordSafetyBox.Dto.UserDto;
import com.tom.passwordSafetyBox.Exception.UserNotFoundException;
import com.tom.passwordSafetyBox.Mapper.UserMapper;
import com.tom.passwordSafetyBox.Repository.UserRepository;
import com.tom.passwordSafetyBox.entity.AppUser;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UserService {
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;


	public UserDto addNewUSer (UserDto userDto) {
		userDto.setEmail(StringEscapeUtils.escapeHtml4(userDto.getEmail()).trim());
		userDto.setPassword(StringEscapeUtils.escapeHtml4(userDto.getPassword()));
		AppUser user = UserMapper.mapToUser(userDto);
		String email = user.getEmail();

		if(!this.userRepository.existsByEmail(email)){

			String password = user.getPassword();
			user.setPassword(passwordEncoder.encode(password));
			AppUser userSaved = this.userRepository.save(user);
			return UserMapper.mapToUserDto(userSaved);}
		else {throw new UserAlreadyExistException("User with email " + email + " already exists.");
		}
	}


	public List<UserDto> getAllUser(){
		List<AppUser>users = this.userRepository.findAll();
		return users.stream().map(user->UserMapper.mapToUserDto(user)).collect(Collectors.toList());
	}
	
	public UserDto loadUserByEmail(String email) {
        return UserMapper.mapToUserDto(this.userRepository.findAppUserByEmail(email));}
	
	
	public UserDto getUserById (Long id){
		AppUser user = this.userRepository.findById(id).orElseThrow(()->new UserNotFoundException("No user found by this id"));
		return UserMapper.mapToUserDto(user);
	}
	
	public void deleteUser (Long id) {
		AppUser user = this.userRepository.findById(id).orElseThrow(()->new UserNotFoundException("No user founf by this id"));
		this.userRepository.delete(user);
	}
	
	

}
