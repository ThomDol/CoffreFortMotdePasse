package com.tom.passwordSafetyBox.Service;

import com.tom.passwordSafetyBox.entity.AppRole;

public interface AppRoleService {
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String email, String roleName);
    void updateRoleFromUser (String email, String roleName);
}
