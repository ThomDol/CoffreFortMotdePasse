package com.tom.passwordSafetyBox.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tom.passwordSafetyBox.entity.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole,Long>{
	 AppRole findByRoleName (String roleName);
}
