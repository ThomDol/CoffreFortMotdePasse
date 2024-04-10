package com.tom.passwordSafetyBox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tom.passwordSafetyBox.Service.AppRoleService;
import com.tom.passwordSafetyBox.Service.UserService;
import com.tom.passwordSafetyBox.entity.AppRole;

import lombok.Data;

@RestController
@RequestMapping (path ="safetybox")

public class AppRoleController {
	@Autowired
	private AppRoleService appRoleService;
	
	@Autowired
	private UserService userService;

	
	 @PostMapping(path = "/roles")
	    public AppRole saveRole(@RequestBody AppRole appRole) {
	        return this.appRoleService.addNewRole(appRole);
	    }
	 
	 @PostMapping(path = "/addRoleToUser")
	    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
	       this.appRoleService.addRoleToUser(roleUserForm.getEmail(),roleUserForm.getRoleName());
	    }
}

@Data
class RoleUserForm {
    private String email;
    private String roleName;
}