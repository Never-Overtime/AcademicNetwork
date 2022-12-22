package com.example.AcademicWebApp.Controllers.RestAPI;

import com.example.AcademicWebApp.Repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class UserController {

    @Autowired
    UsersRepo usersRepo;

    @CrossOrigin(origins = "http://localhost:4200/")

    @GetMapping("/user/{username}")
    public ModelAndView redirectToRole(HttpServletRequest request, @PathVariable("username") String username) {

        UserEntity userEntity = getUser(username);
        if(Objects.equals(userEntity.getRole(), "null"))
            return new ModelAndView("forward:/invalid-user");

        String role = userEntity.getRole();
        //model.addAttribute("role", user.getRole());
        //request.setAttribute("username", username);
        //request.setAttribute("role", role);
        request.setAttribute("userEntity", userEntity);
        request.setAttribute("user", usersRepo.getById(username));


        return new ModelAndView("forward:/{username}/" + role);
    }


    //@GetMapping("/user/{username}")
    public UserEntity getUser(String username){

        if(usersRepo.existsById(username)){

            return new UserEntity(usersRepo.getById(username).getRole().toString());
        }
        else{

            return new UserEntity("null");
        }

    }




}
