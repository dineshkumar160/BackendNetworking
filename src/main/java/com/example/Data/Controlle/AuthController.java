package com.example.Data.Controlle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.example.Data.Domain.User;
import com.example.Data.Service.UserService;


@RestController
@RequestMapping("/aapi")
public class AuthController {

  

    @Autowired
    private UserService service;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        System.out.print(user);
        return service.verify(user);
    }

    }


