package com.example.AcademicWebApp.Services;

import com.example.AcademicWebApp.Controllers.RestAPIs.Entities.Message;
import com.example.AcademicWebApp.Controllers.RestAPIs.UserEntity;
import com.example.AcademicWebApp.Models.User;
import com.example.AcademicWebApp.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("userService")
public class UserService {

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    TeacherRepo teacherRepo;

    @Autowired
    CourseRepo coursesRepo;

    @Autowired
    OptionalCourseRepo optionalCourseRepo;

    @Autowired
    FacultyRepo facultiesRepo;

    @Autowired
    UserDataRepo userDataRepo;

    @Autowired
    GradeRepo gradeRepo;

    @Autowired
    GroupRepo groupRepo;

    @Autowired
    StudentRepo studentRepo;

    /**
     * Checks the credentials against the database's data
     * @param username The username of the user
     * @param pass The password of the user
     * @return A UserEntity object containing a message and the role (null if sth went wrong)
     */
    public UserEntity logIn(String username, String pass) {
        if (usersRepo.existsById(username)) {


            User user = usersRepo.getById(username);
            if (Objects.equals(pass, user.getPassword())) {
                return new UserEntity(user.getRole().toString().toLowerCase(), "Logged in successfully.");
            } else {
                return new UserEntity("null", "Password is not correct.");
            }
        } else {
            return new UserEntity("null", "Username is wrong.");
        }
    }

}
