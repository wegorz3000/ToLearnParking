package com.mobica.rnd.parking.parkingbe.controller;

import com.mobica.rnd.parking.parkingbe.model.User;
import com.mobica.rnd.parking.parkingbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private UserService userService;

    RegistrationController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User registerNewUserAccount(@RequestBody @Valid User user) {
        return userService.addNewUserAccount(user);
    }


}
