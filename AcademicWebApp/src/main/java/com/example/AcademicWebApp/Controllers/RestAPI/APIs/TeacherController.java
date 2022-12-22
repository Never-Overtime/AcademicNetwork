package com.example.AcademicWebApp.Controllers.RestAPI.APIs;

import com.example.AcademicWebApp.Controllers.RestAPI.Entities.Message;
import com.example.AcademicWebApp.Controllers.RestAPI.Entities.StudentGrade;
import com.example.AcademicWebApp.Models.Course;
import com.example.AcademicWebApp.Repositories.CourseRepo;
import com.example.AcademicWebApp.Repositories.FacultyRepo;
import com.example.AcademicWebApp.Repositories.OptionalCourseRepo;
import com.example.AcademicWebApp.Repositories.TeacherRepo;
import com.example.AcademicWebApp.Services.ChiefService;
import com.example.AcademicWebApp.Services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
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

    @Autowired
    TeacherService teacherService;

    @Autowired
    ChiefService chiefService;

    // TEACHER APIS
    @GetMapping("/teacher/getOptionalsForTeacher/{username}")
    public List<Course> getOptionalCoursesOfTeacher(@PathVariable(name = "username") String username){
        return teacherService.getOptionalCoursesOfTeacher(username);
    }

    @GetMapping("/teacher/getAllCourses/{username}")
    public List<Course> getAllCourses(@PathVariable(name = "username") String username){
        return this.teacherService.getCoursesOfTeacher(username);
    }

    @GetMapping("/teacher/getStudentsGradesForCourse/{username}")
    public List<StudentGrade> getAllCourses(@PathVariable(name = "username") String username, @RequestBody Course course){
        return this.teacherService.getStudentsGrades(course);
    }

    @PostMapping("/teacher/proposeOptional/{username}")
    public Message proposeOptional(@PathVariable(name = "username") String username, @RequestBody Course course){
        return teacherService.proposeOptional(username, course);
    }

    @PostMapping("/teacher/addStudentsGrades/{username}")
    public Message addStudentsGrades(@PathVariable(name = "username") String username, @RequestBody List<StudentGrade> studentGrades){
        return teacherService.saveStudentsGrades(studentGrades);
    }

}
