package com.example.school.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final IUserServices userServices;

    @Autowired
    public UserController(final IUserServices userServices){
        this.userServices = userServices;
    }

    @PutMapping(path = "/change_password")
    public ResponseEntity<?> changePassword(@RequestBody final ChangePasswordRequest request, Principal connectedPassword){
        userServices.changePassword(request, connectedPassword);
        return ResponseEntity.ok().build();
    }
    
}
