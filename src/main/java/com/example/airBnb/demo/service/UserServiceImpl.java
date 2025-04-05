package com.example.airBnb.demo.service;

import com.example.airBnb.demo.entity.User;
import com.example.airBnb.demo.exception.ResourceNotFoundException;
import com.example.airBnb.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements  UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: "+id));
    }
}
