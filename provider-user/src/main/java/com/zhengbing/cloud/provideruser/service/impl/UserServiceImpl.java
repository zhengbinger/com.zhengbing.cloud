package com.zhengbing.cloud.provideruser.service.impl;

import com.zhengbing.cloud.provideruser.entity.AuthUser;
import com.zhengbing.cloud.provideruser.repository.UserRepository;
import com.zhengbing.cloud.provideruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Override
    public AuthUser create(String username, String password) {
        AuthUser user = new AuthUser();
        user.setUsername(username);
        password = "{bcrypt}" + passwordEncoder.encode(password);
        user.setPassword(password);
        AuthUser u = userRepository.save(user);
        return u;
    }
}
