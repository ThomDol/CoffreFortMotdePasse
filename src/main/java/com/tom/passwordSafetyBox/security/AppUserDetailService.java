package com.tom.passwordSafetyBox.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tom.passwordSafetyBox.Mapper.UserMapper;
import com.tom.passwordSafetyBox.Service.UserServiceImpl;
import com.tom.passwordSafetyBox.entity.AppUser;

@Service
public class AppUserDetailService  implements UserDetailsService {

	@Autowired
	private UserServiceImpl userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AppUser appUser = UserMapper.mapToUser(this.userService.loadUserByEmail(email));
		AppUser.toString(appUser);
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		appUser.getAppRoles().forEach(role->authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
		System.out.println(authorities);
		return new User(appUser.getEmail(),appUser.getPassword(),authorities);
	}

}
