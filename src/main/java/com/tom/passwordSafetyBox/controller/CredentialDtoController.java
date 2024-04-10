package com.tom.passwordSafetyBox.controller;

import java.util.List;

import com.tom.passwordSafetyBox.Exception.CredentialExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tom.passwordSafetyBox.Dto.CredentialDto;
import com.tom.passwordSafetyBox.Service.CredentialService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/safetybox")
public class CredentialDtoController {
	
	@Autowired
	private CredentialService credentialService;
	
	
	
	
	@PostMapping(path="/credentials/{id}",name="create")
	@ResponseStatus(HttpStatus.CREATED)
	public CredentialDto createCredential(@RequestBody CredentialDto credentialDto,@PathVariable Long id){
		if (credentialService.isCredentialInDataBase(credentialDto.getUrl(), credentialDto.getLoginId())) {
			// Retournez une exception appropriée ou gérez la validation de la manière qui vous convient le mieux
			throw new CredentialExistsException("Credential with URL " + credentialDto.getUrl() + " and login " + credentialDto.getLoginId() + " already exists");
		}
		return  this.credentialService.addCredentialToUSer(credentialDto,id);
		
	}
	
	@DeleteMapping(path="/credentials/{id}", name="delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete (@PathVariable("id") Long id) {
		this.credentialService.deleteCredential(id);
	}
	
	@PutMapping(path="/credentials/{id}", name="update")
	@ResponseStatus(HttpStatus.OK)
	public CredentialDto update(@RequestBody CredentialDto credentialDto,@PathVariable("id") Long id) {
		return this.credentialService.updateCredential(credentialDto, id);
	}
	
	@GetMapping(path="/credentials/{userId}",name="read")
	@ResponseStatus(HttpStatus.OK)
	public List<CredentialDto> getAllEmployees(@PathVariable Long userId){
		return this.credentialService.getAllCredential(userId);
	}

	@GetMapping(path="/credentials/{url}/{login}",name="read")
	@ResponseStatus(HttpStatus.OK)
	public CredentialDto getCredentialByUrlAndLogin(@PathVariable String url,@PathVariable String login){
		return this.credentialService.getCredentialByUrlAndLogin(url,login);
	}
	

}
