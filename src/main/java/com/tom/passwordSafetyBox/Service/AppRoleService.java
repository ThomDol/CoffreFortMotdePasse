package com.tom.passwordSafetyBox.Service;

import org.springframework.stereotype.Service;

import com.tom.passwordSafetyBox.Repository.AppRoleRepository;
import com.tom.passwordSafetyBox.Repository.UserRepository;
import com.tom.passwordSafetyBox.entity.AppRole;
import com.tom.passwordSafetyBox.entity.AppUser;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AppRoleService {
	
	  private AppRoleRepository appRoleRepository;
	  private UserRepository userRepository;

	public AppRoleService(AppRoleRepository appRoleRepository,UserRepository userRepository) {
		this.appRoleRepository = appRoleRepository;
		this.userRepository = userRepository;
	}
	  
	public AppRole addNewRole(AppRole appRole) {
        return this.appRoleRepository.save(appRole); }
	
	public void addRoleToUser(String email, String roleName) {
        AppUser appUser = userRepository.findAppUserByEmail(email);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);}

}


