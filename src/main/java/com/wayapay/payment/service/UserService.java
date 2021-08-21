package com.wayapay.payment.service;


import com.wayapay.payment.dto.TransactionDTO;
import com.wayapay.payment.models.entities.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

}
