package com.example.AcademicWebApp.Controllers.RestAPI;


import com.example.AcademicWebApp.Controllers.RestAPIs.Entities.*;
import com.example.AcademicWebApp.Services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class StaffController  {

    @Autowired
    private StaffService staffService;

//    @CrossOrigin(origins = "http://localhost:4200/")
//    @GetMapping("/staff/facultyGroups")
//    public List<FacultyGroups> getFacultyGroups(@CookieValue(name="username") String username) {
//        return staffService.getFacultiesWithGroups();
//    }
//
//    @CrossOrigin(origins = "http://localhost:4200/")
//    @GetMapping("/staff/facultyGroups/students")
//    public List<Student> getStudentsInGroup(@CookieValue(name="username") String username, @RequestBody Group group) {
//        return staffService.getStudentsFromGroup(group);
//    }

//    @CrossOrigin(origins = "http://localhost:4200/")
//    @GetMapping("/staff/facultyYears")
//    public List<FacultyYears> getFacultyYears(@CookieValue(name="username") String username) {
//        return staffService.getFacultiesWithYears();
//    }
//
//    @CrossOrigin(origins = "http://localhost:4200/")
//    @GetMapping("/staff/facultyYears/students")
//    public List<Student> getFacultyYears(@CookieValue(name="username") String username, @RequestBody FacultyYear facultyYear) {
//        return staffService.getStudentsFromFacultyYear(facultyYear);
//    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping("/staff/faculty/students/{username}")
    public List<StudentAverage> getStudentsFromFacultyYearGroup(@PathVariable(name="username") String username, @RequestBody StaffFilter staffFilter) {
        return staffService.getStudentsFromFacultyYearGroup(staffFilter);
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping("/staff/faculty/getGroups/{username}")
    public List<Integer> getGroupsFromFaculties(@PathVariable(name="username") String username, @RequestBody FacultyYear facultyYear) {
        return staffService.getGroupsByFaculty(facultyYear);
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping(value="/staff/founding/set/{username}")
    public Integer setScholarships(@PathVariable(name="username") String username, @RequestBody FoundingData foundingData) {
        return staffService.setScholarships(foundingData);
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping(value="/staff/founding/{username}")
    public List<StudentGradeStaff> getStudentGrades(@PathVariable(name="username") String username) {
        return staffService.getStudentFounding();
    }

}
