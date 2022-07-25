package com.alkemy.ong.context;

import com.alkemy.ong.ContextTests;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@TestConfiguration
public class UserContextTest {

    @Bean
    @Primary
    public UserDetailsService userDetailsService(){

        UserDetails user = builder("user@gmail.com", "USER");
        UserDetails admin = builder("admin@gmail.com", "ADMIN");
        return new InMemoryUserDetailsManager(List.of(user, admin));
    }

    private UserDetails builder(String email, String role){
        return User.withUsername(email).password("12345678").roles(role).build();
    }

}
