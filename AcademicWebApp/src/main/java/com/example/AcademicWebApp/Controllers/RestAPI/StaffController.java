package com.example.AcademicWebApp.Controllers.RestAPI;


import com.example.AcademicWebApp.Controllers.RestAPI.Entities.*;
import com.example.AcademicWebApp.Services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class StaffController  {

    @Autowired
    private StaffService staffService;

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
