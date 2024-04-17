package com.tom.passwordSafetyBox.Dto;

import java.util.Collection;

import com.tom.passwordSafetyBox.entity.AppRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private Collection<AppRole> appRoles;

}