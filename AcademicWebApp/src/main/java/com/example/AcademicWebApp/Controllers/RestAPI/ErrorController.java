package com.example.AcademicWebApp.Controllers.RestAPI;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class ErrorController {

    //@Autowired
    //UsersRepo usersRepo;

    //@CrossOrigin(origins = "http://localhost:4200/")

    @RequestMapping ("/invalid-user")
    public String InvalidUserError()
    {
        return "This user does not exist.";
    }





}
