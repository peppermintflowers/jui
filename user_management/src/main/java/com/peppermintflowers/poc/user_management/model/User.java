package com.peppermintflowers.poc.user_management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Document("users")
public class User implements UserDetails {
    @Id
    public String username;
    public String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    public User(){

    }
    public void setPassword(String password){
        this.password = password;
}
    public String getUsername(){
        return this.username;
}
    public String getPassword() {
        return this.password;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

}
