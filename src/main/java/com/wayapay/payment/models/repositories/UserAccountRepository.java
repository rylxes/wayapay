package com.wayapay.payment.models.repositories;

import com.wayapay.payment.models.entities.User;
import com.wayapay.payment.models.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends JpaSpecificationExecutor<UserAccount>, CrudRepository<UserAccount, Long> {

    @Transactional
    @Query(value = "from #{#entityName} where username =:userName and customerAccountId =:accountNumber")
    Optional<UserAccount> findAccountWithUserAndAccount(@Param("userName") String userName, @Param("accountNumber") String accountNumber);

    @Transactional
    @Query(value = "from #{#entityName} where customerAccountId =:accountNumber")
    Optional<UserAccount> findAccountWithUser(@Param("accountNumber") String accountNumber);

}
