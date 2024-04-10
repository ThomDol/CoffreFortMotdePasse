package com.tom.passwordSafetyBox.Dto;

import com.tom.passwordSafetyBox.entity.AppUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CredentialDto {
	
	private Long id;
	private String url;
	private String loginId;
	private String password;
	

}
