package com.mobica.rnd.parking.parkingbe.service;

import com.mobica.rnd.parking.parkingbe.model.User;
import com.mobica.rnd.parking.parkingbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    UserServiceImpl(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User addNewUserAccount(User user) {
        return userRepository.insert(user);
    }
}



