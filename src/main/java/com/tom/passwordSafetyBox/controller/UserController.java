package com.tom.passwordSafetyBox.controller;

import java.util.List;

import com.tom.passwordSafetyBox.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.tom.passwordSafetyBox.Dto.UserDto;
import com.tom.passwordSafetyBox.Service.UserService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/safetybox")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping(path="/users",name="create")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto createEmployee(@RequestBody UserDto UserDto){
		return  this.userService.addNewUSer(UserDto);}

	
	@GetMapping(path="/users",name="read")
	@ResponseStatus(HttpStatus.OK)
	public List<UserDto> getAllUsers(){
		return this.userService.getAllUser();
	}
	
	@DeleteMapping(path="/users/{id}", name="delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser (@PathVariable Long id) {
		this.userService.deleteUser(id);
	}

	@GetMapping(path="/users/{id}",name="read")
	@ResponseStatus(HttpStatus.OK)
	public UserDto getOneUserById (@PathVariable Long id){
		return this.userService.getUserById(id);
	}


	@GetMapping(path="/users/{email}",name="read")
	@ResponseStatus(HttpStatus.OK)
	public UserDto getUserByEmail (@PathVariable String email){
		return UserMapper.mapToUserDto(this.userService.loadUserByEmail(email));
	}
	
	

}
