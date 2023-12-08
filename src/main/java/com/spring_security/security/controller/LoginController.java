package com.spring_security.security.controller;

import com.spring_security.security.Models.UserAccount;
import com.spring_security.security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepo;
    //disable csrf because post requests will be blocked
    //send the body of customer and save it in repo

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserAccount userAccount) {
        UserAccount savedCustomer = null;
        ResponseEntity response = null;
        try {
            String hashPwd = passwordEncoder.encode(userAccount.getPassword());
            userAccount.setPassword(hashPwd);
            savedCustomer = userRepo.save(userAccount);
            if (savedCustomer.getId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED).body("User created");
            }
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception occured due to" + e.getMessage());
        }
        return response;
    }

    @RequestMapping("/user")
    public UserAccount getUserDetailsAfterLogin(Authentication authentication) {
        List<UserAccount> userAccount = userRepo.findByEmail(authentication.getName());
        if (userAccount.size() > 0) {
            return userAccount.get(0);
        } else {
            return null;
        }
    }
}
