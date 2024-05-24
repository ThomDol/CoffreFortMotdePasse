package com.tom.passwordSafetyBox.controller;

import java.util.List;

import com.tom.passwordSafetyBox.Service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.tom.passwordSafetyBox.Dto.CredentialDto;


import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/safetybox")
public class CredentialDtoController {
	
	@Autowired
	private CredentialService credentialService;
	
	
	
	
	@PostMapping(path="/credentials/{id}",name="create")
	@ResponseStatus(HttpStatus.CREATED)
	public CredentialDto createCredential(@RequestBody CredentialDto credentialDto,@PathVariable Long id) throws Exception {

		return  this.credentialService.addCredentialToUSer(credentialDto,id);
		
	}
	
	@DeleteMapping(path="/credentials/{id}", name="delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete (@PathVariable("id") Long id) {
		this.credentialService.deleteCredential(id);
	}
	
	@PutMapping(path="/credentials/{id}", name="update")
	@ResponseStatus(HttpStatus.OK)
	public CredentialDto update(@RequestBody CredentialDto credentialDto,@PathVariable("id") Long id) throws Exception {
		return this.credentialService.updateCredential(credentialDto, id);
	}
	@GetMapping(path="/credentials/ById/{id}",name="read")
	@ResponseStatus(HttpStatus.OK)
	public CredentialDto getOneCredentialById (@PathVariable Long id) throws Exception {
		return this.credentialService.getCredentialById(id);
	}
	
	@GetMapping(path="/credentials/{userId}",name="read")
	@ResponseStatus(HttpStatus.OK)
	public List<CredentialDto> getAllEmployees(@PathVariable Long userId) throws Exception {
		return this.credentialService.getAllCredential(userId);
	}

	@GetMapping(path="/credentials/{url}/{login}",name="read")
	@ResponseStatus(HttpStatus.OK)
	public CredentialDto getCredentialByUrlAndLogin(@PathVariable String url,@PathVariable String login){
		return this.credentialService.getCredentialByUrlAndLogin(url,login);
	}
	

}
