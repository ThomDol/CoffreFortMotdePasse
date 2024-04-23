package com.tom.passwordSafetyBox.controller;

import java.security.Principal;
import java.util.List;

import com.tom.passwordSafetyBox.Exception.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tom.passwordSafetyBox.Dto.UserDto;
import com.tom.passwordSafetyBox.Mapper.UserMapper;
import com.tom.passwordSafetyBox.Service.UserService;

import lombok.AllArgsConstructor;


@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/safetybox")
public class UserController {

	@Autowired
	private UserService userService;


	@PostMapping(path="/addUsers", name="create")
	public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
		try {
			UserDto createdUser = userService.addNewUSer(userDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
		} catch (UserAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User with email " + userDto.getEmail() + " already exists.");
		}
	}


	@GetMapping(path="/users",name="read")
	@ResponseStatus(HttpStatus.OK)
	public List<UserDto> getAllUsers(){
		return this.userService.getAllUser();
	}

	@GetMapping(path="/profile",name="read")
	@ResponseStatus(HttpStatus.OK)
	public UserDto profile (Principal principal){
		return this.userService.loadUserByEmail(principal.getName());
	}

	@DeleteMapping(path="/users/{id}", name="delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser (@PathVariable Long id) {
		this.userService.deleteUser(id);
	}

	@GetMapping(path="/users/getByEmail/{email}",name="read")
	@ResponseStatus(HttpStatus.OK)
	public UserDto getUserByEmail (@PathVariable String email){
		return this.userService.loadUserByEmail(email);
	}
	@GetMapping(path="/users/{id}",name="read")
	@ResponseStatus(HttpStatus.OK)
	public UserDto getUserById (@PathVariable Long id){
		return this.userService.getUserById(id);
	}





}
