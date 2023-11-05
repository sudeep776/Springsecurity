package com.spring_security.security.Config;

import com.spring_security.security.Models.Authority;
import com.spring_security.security.Models.UserAccount;
import com.spring_security.security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UsernamePwdAuthProvider implements AuthenticationProvider {
    //provider sends it to providerManager which authenticates
    //check if the hashed password if it matches return new UsernamePasswordAuthenticationToken
    @Autowired
    private UserRepository customerRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        List<UserAccount> user = customerRepo.findByEmail(username);
        if (user.size() > 0) {
            if (passwordEncoder.matches(pwd, user.get(0).getPassword())) {
                //we are going to add multiple authorities mapped to single user
//                List<GrantedAuthority> authorities = new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
                  return new UsernamePasswordAuthenticationToken(username,pwd,getGrantedAuthorities(user.get(0).getAuthorities()));
            } else {
                throw new BadCredentialsException("Invalid password");
            }
        }
        else{
            throw new BadCredentialsException("No user registered with details");
        }
    }

    public List<GrantedAuthority> getGrantedAuthorities(Set<Authority>authorities){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Authority authority:authorities){
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
