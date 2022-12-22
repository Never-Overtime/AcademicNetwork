package com.example.AcademicWebApp.Controllers.RestAPIs;


import com.example.AcademicWebApp.Models.*;
import com.example.AcademicWebApp.Repositories.StudentRepo;
import com.example.AcademicWebApp.Services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200/")
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
    public Student getStudentByUsername(@PathVariable("username") String username)
    {
        return studentRepo.findByUsername(username);
    }

    @GetMapping("/student/group1/{group1}")
    public List<Student> getStudentsByFirstGroup(@PathVariable("group1") String group1)
    {
        return studentRepo.findAllByGroup1(Integer.valueOf(group1));
    }

    @GetMapping("/student/group2/{group2}")
    public List<Student> getStudentsBySecondGroup(@PathVariable("group2") String group2)
    {
        return studentRepo.findBySecondGroup(Integer.valueOf(group2));
    }

//    @GetMapping("/student/name/{name}")
//    public List<Student> getStudentByName(@PathVariable("name") String name)
//    {
//        return studentRepo.findByName(name);
//    }

    // (✿◠‿◠)
    @PostMapping(value = "/student/add",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Student addStudent(@RequestBody StudentData student)
    {

        return studentService.saveStudent(student);
    }



    @GetMapping("/student/getCoursesFirstGroup/{username}")
    public List<Course> getCoursesForStudentFirstGroup(@PathVariable(name = "username") String username)
    {
        return studentService.getCoursesForFirstGroup(username);

    }

    @GetMapping("/student/getCoursesSecondGroup/{username}")
    public List<Course> getCoursesForStudentSecondGroup(@PathVariable(name = "username") String username)
    {
        return studentService.getCoursesForSecondGroup(username);

    }

    //DONE TODO #1 get api -  make api to get optionals based on faculty, year
    @GetMapping("/student/getOptionalsByFacultyAndYear")
    public List<Course> getOptionalsByFacultyAndYear(@RequestBody OptionalCourseData data)
    {
        return studentService.getOptionalsBasedOnFacultyAndYear(data);
    }

    //DONE TODO #2 get api - make api to get all faculties (as faculties)
    @GetMapping("/student/getFaculties")
    public List<Faculty> getFacultiesForStudent()
    {
        return studentService.getFaculties();
    }


    //DONE TODO #3 post api - list of courses, 100% optionals
    //DONE TODO #4 service stuff - insert in optional_course_enrollment data based on the student and the list from #3
    @PostMapping(value="/student/sendOptionalsPreferences/{username}",
            consumes = "application/json",
            produces = "application/json")
    public List<OptionalCourseEnrollment> sendOptionalsPreferences(@RequestBody List<Course> courses, @PathVariable(name = "username") String username)
    {
        return studentService.sendOptionalsPreferences(courses, username);
    }


    //DONE TODO #5 get api - return list of all courses in which the student is enrolled (the first part is already implemented above these TODOS, hutsu) , including the optional one
    @GetMapping("/student/getAllCoursesForWhichEnrolled/{username}")
    public List<Course> getAllCoursesForWhichEnrolled(@PathVariable(name = "username") String username)
    {
        return studentService.getAllCoursesForWhichEnrolled(username);
    }



    //DONE TODO #n get api - get faculties+years that the student is enrolled in, based on username(cookie)
    @GetMapping("/student/getFacultiesAndYears/{username}")
    public List<FacultyAndYearData> getFacultiesAndYears(@PathVariable(name = "username") String username)
    {
        return studentService.getFacultiesAndYears(username);
    }

    //DONE TODO #n+1 get api - get courses+grades based on faculty and year (plus username from cookie) + (optional course + grade)
    @PostMapping(value="/student/getGrades/{username}",
            consumes = "application/json",
            produces = "application/json")
    public List<CourseGradeData> getGrades(@PathVariable(name = "username") String username,@RequestBody FacultyAndYearData data)
    {
        return studentService.getGrades(username, data);
    }
    //TODO DEADLINE 21 MAY 13:00 (all done at 3:26)
    //(✿◠‿◠)


    public String sayHello(UserEntity user)
    {
        return "You are a " + user.getRole() + ". This should be that page where you can edit your profile and etc.";
    }



}
