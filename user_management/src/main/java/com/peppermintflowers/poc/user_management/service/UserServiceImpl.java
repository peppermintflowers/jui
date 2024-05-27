package com.peppermintflowers.poc.user_management.service;

import com.peppermintflowers.poc.user_management.model.User;
import com.peppermintflowers.poc.user_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public void saveUser(User user) {
        User persistUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()));
        userRepository.save(persistUser);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password)  {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return userRepository.findUserByUsername(username).orElseThrow();

    }

}
