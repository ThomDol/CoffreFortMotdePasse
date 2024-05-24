package com.tom.passwordSafetyBox.Service;

import com.tom.passwordSafetyBox.Dto.CredentialDto;

import java.util.List;

public interface CredentialService {
    void deleteCredential (Long id);
    CredentialDto updateCredential (CredentialDto credentialDto, Long id) throws Exception;
    CredentialDto getCredentialById (Long id) throws Exception;
    List<CredentialDto> getAllCredential (Long userId) throws Exception;
    CredentialDto addCredentialToUSer (CredentialDto credentialDto,Long userId) throws Exception;
    CredentialDto getCredentialByUrlAndLogin (String url,String login);

}
