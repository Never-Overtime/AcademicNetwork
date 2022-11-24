package com.example.AcademicWebApp.Controllers.RestAPIs;


import com.example.AcademicWebApp.Models.Student;
import com.example.AcademicWebApp.Models.StudentData;
import com.example.AcademicWebApp.Repositories.StudentRepo;
import com.example.AcademicWebApp.Repositories.UsersRepo;
import com.example.AcademicWebApp.Services.StudentService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.Origin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
//@RequestMapping("/")
public class StudentController {

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private StudentService studentService;

    //@CrossOrigin(origins = "http://localhost:4200/")

    @GetMapping("/{username}/Student")
    public UserEntity getStudent(@PathVariable("username") String username, HttpServletRequest request)
    {
        //return "Hello, " + user. + "! You are a " + request.getAttribute("role");
        return (UserEntity) request.getAttribute("userEntity");
    }

    @RequestMapping("/students")
    public String getStudents()
    {
        return studentRepo.findAll().toString();
    }

    @GetMapping("/student/{username}")
    public String getStudentByUsername(@PathVariable("username") String username)
    {
        return studentRepo.findById(username).toString();
    }

    @GetMapping("/student/group1/{group1}")
    public List<Student> getStudentsByFirstGroup(@PathVariable("group1") String group1)
    {
        return studentRepo.findAllByGroup1(Integer.valueOf(group1));
    }

    @GetMapping("/student/group2/{group2}")
    public String getStudentsBySecondGroup(@PathVariable("group2") String group2)
    {
        return studentRepo.findBySecondGroup(Integer.valueOf(group2)).toString();
    }

    @GetMapping("/student/name/{name}")
    public List<Student> getStudentByName(@PathVariable("name") String name)
    {
        return studentRepo.findByName(name);
    }

    @PostMapping(value = "/student/add",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Student addStudent(@RequestBody StudentData student)
    {

        return studentService.saveStudent(student);
    }

    public String sayHello(UserEntity user)
    {
        return "You are a " + user.getRole() + ". This should be that page where you can edit your profile and etc.";
    }
    
}
