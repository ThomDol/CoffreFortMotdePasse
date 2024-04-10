package com.tom.passwordSafetyBox.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tom.passwordSafetyBox.entity.Credential;

public interface CredentialRepository extends JpaRepository<Credential,Long>{
List<Credential> findAllByUserId(Long userId);
Credential findByUrlAndLoginId(String url,String loginId);
Boolean existsCredentialByUrlAndLoginId(String url,String loginId);
}
