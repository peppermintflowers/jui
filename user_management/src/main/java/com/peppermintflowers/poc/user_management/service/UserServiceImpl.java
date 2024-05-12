package com.peppermintflowers.poc.user_management.service;

import com.peppermintflowers.poc.user_management.model.User;
import com.peppermintflowers.poc.user_management.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl  implements UserService{

    private UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
    }
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
