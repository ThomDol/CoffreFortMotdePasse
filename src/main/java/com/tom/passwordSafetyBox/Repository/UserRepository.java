package com.tom.passwordSafetyBox.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tom.passwordSafetyBox.entity.AppUser;

public interface UserRepository extends JpaRepository<AppUser,Long> {
	AppUser findAppUserByEmail (String email);

}
