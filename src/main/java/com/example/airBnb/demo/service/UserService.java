package com.example.airBnb.demo.service;

import com.example.airBnb.demo.dto.ProfileUpdateRequestDto;
import com.example.airBnb.demo.dto.UserDto;
import com.example.airBnb.demo.entity.User;

public interface UserService {

    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();

}
