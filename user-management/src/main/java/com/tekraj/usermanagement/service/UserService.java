package com.tekraj.usermanagement.service;

import java.util.List;

import com.tekraj.usermanagement.dto.UserDto;
import com.tekraj.usermanagement.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}