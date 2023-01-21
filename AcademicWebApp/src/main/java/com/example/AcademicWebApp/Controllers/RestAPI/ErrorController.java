package com.example.AcademicWebApp.Controllers.RestAPI;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class ErrorController {

    @RequestMapping ("/invalid-user")
    public String InvalidUserError()
    {
        return "This user does not exist.";
    }





}
