package com.planepockets.controller;

import com.planepockets.pojo.B2bPojo;
import com.planepockets.pojo.SimpleResponse;
import com.planepockets.proton.mailservice.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/b2b")
public class B2bController {

    @Autowired
    private MailService mailService;

    @PostMapping
    public ResponseEntity<SimpleResponse> getB2bRequest(@RequestBody B2bPojo request) {
        //mailService.sendUserInfoMail(request.getName(), "Ashi21091998@gmail.com", request);
        return new ResponseEntity<>(new SimpleResponse("Your response recorded successfully"), HttpStatus.OK);
    }
}
