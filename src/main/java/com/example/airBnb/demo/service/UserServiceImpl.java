package com.example.airBnb.demo.service;

import com.example.airBnb.demo.dto.ProfileUpdateRequestDto;
import com.example.airBnb.demo.dto.UserDto;
import com.example.airBnb.demo.entity.User;
import com.example.airBnb.demo.exception.ResourceNotFoundException;
import com.example.airBnb.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.airBnb.demo.util.AppUtils.getCurrentUser;

@Service
public class UserServiceImpl implements  UserService, UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: "+id));
    }

    @Override
    public void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto) {
        User user = getCurrentUser();

        if(profileUpdateRequestDto.getDateOfBirth()!=null) user.setDateOfBirth(profileUpdateRequestDto.getDateOfBirth());
        if(profileUpdateRequestDto.getGender()!=null) user.setGender(profileUpdateRequestDto.getGender());
        if(profileUpdateRequestDto.getName()!=null) user.setName(profileUpdateRequestDto.getName());

        userRepository.save(user);
    }

    @Override
    public UserDto getMyProfile() {
        User user = getCurrentUser();
        log.info("getting the profile for user with id:{} ",user.getId());
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElse(null);
    }
}
