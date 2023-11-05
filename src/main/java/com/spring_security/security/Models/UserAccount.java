package com.spring_security.security.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customer")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String email;
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String role;
    @JsonIgnore
    @OneToMany(mappedBy = "userAccount",fetch = FetchType.EAGER)
    private Set<Authority> authorities;
}
