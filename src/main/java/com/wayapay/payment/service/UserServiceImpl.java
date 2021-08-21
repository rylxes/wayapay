package com.wayapay.payment.service;

import com.wayapay.payment.models.entities.User;
import com.wayapay.payment.models.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    public final UserRepository userDetailsService;

    public UserServiceImpl(UserRepository userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDetailsService.findByUsername(username);
    }


}
