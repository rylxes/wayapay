package com.wayapay.payment.misc;


import com.wayapay.payment.models.entities.User;
import com.wayapay.payment.models.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserLoader implements CommandLineRunner {

    public final UserRepository userRepository;
    public final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserLoader(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
    }

    private void loadUsers() {
        if (userRepository.count() == 0) {
            User one = userRepository.save(
                    User.builder()
                            .fullName("John doe")
                            .username("john")
                            .email("john@john.com")
                            .password(bCryptPasswordEncoder.encode("password"))
                            .build()
            );
            log.info("Sample Users Loaded");
        }
    }
}
