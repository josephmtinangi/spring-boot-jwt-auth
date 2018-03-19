package com.example.hello.controllers;

import com.example.hello.models.User;
import com.example.hello.repositories.UserRepository;
import com.example.hello.utilities.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(path = "/u")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> index() {

        List<User> users = userRepository.findAll();

        return Helper.createResponse(true, users, null, HttpStatus.OK);
    }

    @RequestMapping(path = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathVariable String username) {

        User user = userRepository.findFirstByUsername(username);

        if (user == null) {
            Helper.createResponse(false, null, "User not found", HttpStatus.BAD_REQUEST);
        }

        return Helper.createResponse(true, user, null, HttpStatus.OK);
    }
}
