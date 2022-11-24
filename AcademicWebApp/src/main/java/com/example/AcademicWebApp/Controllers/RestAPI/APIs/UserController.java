package com.example.AcademicWebApp.Controllers.RestAPIs;
import com.example.AcademicWebApp.Repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
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
