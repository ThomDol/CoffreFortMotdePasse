package com.tom.passwordSafetyBox.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.tom.passwordSafetyBox.crypto.Crypto;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;

import com.tom.passwordSafetyBox.Dto.CredentialDto;
import com.tom.passwordSafetyBox.Exception.CredentialNotFoundException;
import com.tom.passwordSafetyBox.Exception.UserNotFoundException;
import com.tom.passwordSafetyBox.Mapper.CredentialMapper;
import com.tom.passwordSafetyBox.Repository.CredentialRepository;
import com.tom.passwordSafetyBox.Repository.UserRepository;
import com.tom.passwordSafetyBox.entity.Credential;
import com.tom.passwordSafetyBox.entity.AppUser;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CredentialServiceImpl implements CredentialService {
	private CredentialRepository credentialRepository;
	private UserRepository userRepository;
	
		
	public void deleteCredential (Long id) {
		
		Credential credentialToDelete = this.credentialRepository.findById(id).orElseThrow(()->new CredentialNotFoundException("Credential not found"));
		this.credentialRepository.delete(credentialToDelete);
	}
	
	public CredentialDto updateCredential (CredentialDto credentialDto,Long id) throws Exception {
		Credential credential = this.credentialRepository.findById(id).orElseThrow(()->new CredentialNotFoundException("Credential not found")); 
		credential.setUrl(credentialDto.getUrl());
		credential.setLoginId(credentialDto.getLoginId());
		credential.setPassword(Crypto.cryptService(credentialDto.getPassword()));
		return CredentialMapper.mapToCredentialDto(this.credentialRepository.save(credential));
	}

	public CredentialDto getCredentialById (Long id) throws Exception {
		Credential credential = this.credentialRepository.findById(id).orElseThrow(()->new CredentialNotFoundException("Credential not found"));
		credential.setPassword(Crypto.decryptService(credential.getPassword()));
		return CredentialMapper.mapToCredentialDto(credential);
	}
	
	public List<CredentialDto> getAllCredential (Long userId) throws Exception {
		List<Credential>credentials = this.credentialRepository.findAllByUserId(userId);
		for(Credential credential:credentials){
			credential.setPassword(Crypto.decryptService(credential.getPassword()));
		}
		return credentials.stream().map(CredentialMapper::mapToCredentialDto).toList();
	}
	
	public CredentialDto addCredentialToUSer (CredentialDto credentialDto,Long userId) throws Exception {
		AppUser user = this.userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("user non connecté"));
		Credential credential = new Credential();
        credential.setUrl(StringEscapeUtils.escapeHtml4(credentialDto.getUrl()));
        credential.setLoginId(StringEscapeUtils.escapeHtml4(credentialDto.getLoginId()));
        credential.setPassword(Crypto.cryptService(StringEscapeUtils.escapeHtml4(credentialDto.getPassword())));
        credential.setUser(user);
        Credential savedCredential = credentialRepository.save(credential);
        return CredentialMapper.mapToCredentialDto(savedCredential);
	}

	public CredentialDto getCredentialByUrlAndLogin (String url,String login){
		Credential credential = this.credentialRepository.findByUrlAndLoginId(url,login);
		return CredentialMapper.mapToCredentialDto(credential);

	}



}

