package com.spring_security.security.Config;

import com.spring_security.security.Models.UserAccount;
import com.spring_security.security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

//@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    UserRepository customerRepo;
    // loadUserbyusername sends to DAOAuthprovider --- logic/ if user doesnt exist exception else return new user with (username,password,authorities)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName,password;
        List<UserAccount> user = customerRepo.findByEmail(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(user.size()==0){
            throw new UsernameNotFoundException("User details not found with username"+username);
        }
        else{
            userName = user.get(0).getEmail();
            password = user.get(0).getPassword();
            authorities.add(new SimpleGrantedAuthority(user.get(0).getRole()));
        }
        return new User(userName,password,authorities);
    }
}
