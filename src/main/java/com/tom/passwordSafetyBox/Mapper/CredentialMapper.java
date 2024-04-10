package com.tom.passwordSafetyBox.Mapper;

import com.tom.passwordSafetyBox.Dto.CredentialDto;
import com.tom.passwordSafetyBox.entity.Credential;

public class CredentialMapper {
	
	public static CredentialDto mapToCredentialDto(Credential credential) {
		CredentialDto credentialDto = new CredentialDto();
        credentialDto.setId(credential.getId());
        credentialDto.setUrl(credential.getUrl());
        credentialDto.setLoginId(credential.getLoginId());
        credentialDto.setPassword(credential.getPassword());
        return credentialDto;
	}
	
	
	public static Credential mapToCredential (CredentialDto credentialDto) {
		Credential credential = new Credential();
        credential.setId(credentialDto.getId());
        credential.setUrl(credentialDto.getUrl());
        credential.setLoginId(credentialDto.getLoginId());
        credential.setPassword(credentialDto.getPassword());
        return credential;
	}

}

