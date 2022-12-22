package com.example.AcademicWebApp.Controllers.RestAPI.APIs;

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
        LocalDate cd = LocalDate.now();
        return optionalCourseRepo.findOptionalCoursesByUsername(username, cd.getYear() + 1);
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/chief/getAllOptionalsForApproval")
    public List<Course> getAllOptionalsForApproval(@CookieValue(name = "username") String username) {
        LocalDate cd = LocalDate.now();
        // getting the faculty id that the teacher is as a chief

        List<Faculty> f = facultiesRepo.getFacultyForChief(username);
        if (f.size() >= 1) {
            return optionalCourseRepo.getAllOptionalsForApproval(cd.getYear() + 1, f.get(0).getFid());
        }
        else{
            return new ArrayList<Course>();
        }
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping("/teacher/proposeOptional")
    public Message proposeOptional(@CookieValue(name = "username") String username, @RequestBody Course course){
        // checking if he can add another optional course (ge can update it tho, and that will be okay)
        LocalDate cd = LocalDate.now();
        List<Course> optionalCoursesAlreadyAdded = optionalCourseRepo.findOptionalCoursesByUsername(username, cd.getYear() + 1);

        int nrOfCoursesDifferent = 0;

        for(Course c : optionalCoursesAlreadyAdded){
            if(c.getCid() != course.getCid()) {
                nrOfCoursesDifferent = nrOfCoursesDifferent + 1;
            }
        }
        // if there are more than 1 course that is different than the one sent rn
        // it means that it s neither updating, and it is adding a 3rd optional course, which he isnt allowed.
        if(nrOfCoursesDifferent > 1){
            return new Message("You already added 2 optional courses.");
        }

        try{
            coursesRepo.save(course);
            OptionalCourse oc = new OptionalCourse(course.getCid(), username);
            optionalCourseRepo.save(oc);
        }
        catch (Exception e){
            return new Message("Sth bad happened: " + Arrays.toString(e.getStackTrace()));
        }

        return new Message("All g. Course added and it is an optional course.");
    }


}
