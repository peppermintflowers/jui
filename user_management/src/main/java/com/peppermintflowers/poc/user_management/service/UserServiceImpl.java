package com.peppermintflowers.poc.user_management.service;

import com.peppermintflowers.poc.user_management.model.User;
import com.peppermintflowers.poc.user_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl  implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public Optional<User> registerUser(User user) {
        Optional<User> existingUser = userRepository.findUserByUsername(user.getUsername());
        if(existingUser.isPresent()){
            return Optional.empty();
        }
        else {
            User persistUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()));
            String userId = generateNextId();
            persistUser.setId(userId);

            try {
                return Optional.of(userRepository.save(persistUser));
            }
            catch(Exception e){
                log.error(
                        "Unable to create user Exception:{}",e.toString()
                );
                return Optional.empty();
            }
        }
    }

    private String generateNextId() {
        return UUID.randomUUID().toString();
    }


    @Override
    public User getUserByUsernameAndPassword(String username, String password)  {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return userRepository.findUserByUsername(username).orElseThrow();

    }

}
