package com.sa.tawuniya.assingment.account.security;

import com.sa.tawuniya.assingment.account.exception.AccountException;
import com.sa.tawuniya.assingment.account.exception.enums.AccountResponseErrorCode;
import com.sa.tawuniya.assingment.account.model.dto.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.sa.tawuniya.assingment.account.constants.ApplicationConstants.*;
import static com.sa.tawuniya.assingment.account.constants.ErrorConstants.USER_NOT_FOUND;

@Service
public class InMemoryUserDetailsService implements UserDetailsService {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public InMemoryUserDetailsService() {
        users.put(USER,new User(USER, USER, List.of(USER_ROLE)));
        users.put(ADMIN, new User(ADMIN, ADMIN, List.of(ADMIN_ROLE)));
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(users.get(username))
                .map(this::getUser)
                .orElseThrow(()-> new AccountException( AccountResponseErrorCode.NOT_FOUND, String.format( USER_NOT_FOUND)));
    }

    private UserDetails getUser(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.username())
                .password(user.password())
                .roles(user.roles().toArray(new String[0]))
                .build();
    }
}
