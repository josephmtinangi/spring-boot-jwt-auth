package com.example.hello.controllers;

import com.example.hello.utilities.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/")
public class WelcomeController {

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<?> index() {

        return Helper.createResponse(true, null, "Welcome", HttpStatus.OK);
    }
}
