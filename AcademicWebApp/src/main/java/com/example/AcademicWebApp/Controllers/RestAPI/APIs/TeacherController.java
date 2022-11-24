package com.example.AcademicWebApp.Controllers.RestAPIs.APIs;

import com.example.AcademicWebApp.Controllers.RestAPIs.Entities.Message;
import com.example.AcademicWebApp.Models.Course;
import com.example.AcademicWebApp.Models.Faculty;
import com.example.AcademicWebApp.Models.OptionalCourse;
import com.example.AcademicWebApp.Repositories.CourseRepo;
import com.example.AcademicWebApp.Repositories.FacultyRepo;
import com.example.AcademicWebApp.Repositories.OptionalCourseRepo;
import com.example.AcademicWebApp.Repositories.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TeacherController {

    @Autowired
    TeacherRepo teacherRepo;

    @Autowired
    CourseRepo coursesRepo;

    @Autowired
    OptionalCourseRepo optionalCourseRepo;

    @Autowired
    FacultyRepo facultiesRepo;

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/teacher/getOptionalsForTeacher")
    public List<Course> getOptionalCoursesOfTeacher(@CookieValue(name = "username") String username){
        // to be completed
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/chief/getAllOptionalsForApproval")
    public List<Course> getAllOptionalsForApproval(@CookieValue(name = "username") String username) {
        // to be completed
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping("/teacher/proposeOptional")
    public Message proposeOptional(@CookieValue(name = "username") String username, @RequestBody Course course){
        // checking if he can add another optional course (ge can update it tho, and that will be okay
    }


}
