package com.tom.passwordSafetyBox.Service;

import com.tom.passwordSafetyBox.Dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addNewUSer (UserDto userDto);
    List<UserDto> getAllUser();
    UserDto loadUserByEmail(String email);
    UserDto getUserById (Long id);
    void deleteUser (Long id);
}
