package com.example.hello.auth;

import com.example.hello.models.User;
import com.example.hello.repositories.UserRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Assert.notNull(authentication, "No authentication data provided");

        String username = (String) authentication.getPrincipal();
        System.out.println("username = " + username);
        String password = (String) authentication.getCredentials();
        System.out.println("password = " + password);

        User user = userRepository.findFirstByUsername(username);
        System.out.println("User = " + user);
        System.out.println("username = " + user.getUsername());
        System.out.println("password = " + user.getPassword());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (user == null || !bCryptPasswordEncoder.matches(password, user.getPassword())) {
            System.out.println("Authentication Failed. Username or password is incorrect");
            throw new BadCredentialsException("Authentication Failed. Username or password is incorrect");
        }

        UserContext userContext = new UserContext(user.getId(), user.getUsername());

        return new UsernamePasswordAuthenticationToken(userContext, null, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
