package com.wayapay.payment.models.repositories;

import com.wayapay.payment.models.entities.Transactions;
import com.wayapay.payment.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaSpecificationExecutor<User>, CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
