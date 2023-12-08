package com.spring_security.security.controller;

import com.spring_security.security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserRepository customerRepo;
    @GetMapping("/message")
    public String getMessage(){
        return "Hello you are JWT Authenticated";
    }

//    @GetMapping("/{email}")
//    public List<Customer> getByEmail(@PathVariable("email") String email){
//        return customerRepo.findByEmail(email);
//    }
}
