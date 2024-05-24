package com.tom.passwordSafetyBox.Service;

import org.springframework.stereotype.Service;

import com.tom.passwordSafetyBox.Repository.AppRoleRepository;
import com.tom.passwordSafetyBox.Repository.UserRepository;
import com.tom.passwordSafetyBox.entity.AppRole;
import com.tom.passwordSafetyBox.entity.AppUser;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AppRoleServiceImpl implements AppRoleService {
	
	  private final AppRoleRepository appRoleRepository;
	  private final UserRepository userRepository;

	public AppRoleServiceImpl(AppRoleRepository appRoleRepository, UserRepository userRepository) {
		this.appRoleRepository = appRoleRepository;
		this.userRepository = userRepository;
	}
	  
	public AppRole addNewRole(AppRole appRole) {
        return this.appRoleRepository.save(appRole); }
	
	public void addRoleToUser(String email, String roleName) {
        AppUser appUser = userRepository.findAppUserByEmail(email);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);}

	public void updateRoleFromUser (String email, String roleName){
		AppUser appUser = userRepository.findAppUserByEmail(email);
		AppRole appRole = appRoleRepository.findByRoleName(roleName);
		if (appUser.getAppRoles().isEmpty()){
			appUser.getAppRoles().add(appRole);
		}
		else{
			appUser.getAppRoles().clear();
			appUser.getAppRoles().add(appRole);
		}
	}

}


