package com.football.app.service;


import com.football.app.entity.UserInfo;
import com.football.app.repository.UserInfoRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserInfoService {

    List<UserInfo> productList = null;

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    
    public List<UserInfo> getProducts() {
        return repository.findAll();
    }

    public UserInfo getProduct(int id) {
         UserInfo userInfo = repository.findById(id).get();
         return userInfo;
    }


    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "user added to system ";
    }
}
