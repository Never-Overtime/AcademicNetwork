package com.example.AcademicWebApp.Controllers.RestAPI;

import com.example.AcademicWebApp.Repositories.UsersRepo;
import com.example.AcademicWebApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class UserController {

    @Autowired
    UsersRepo usersRepo;
    @Autowired
    UserService userService;

    @GetMapping("/user/{username}")
    public UserEntity getUser(@PathVariable("username") String username, @RequestParam("password") String pass){
        return userService.logIn(username, pass);
    }




}
