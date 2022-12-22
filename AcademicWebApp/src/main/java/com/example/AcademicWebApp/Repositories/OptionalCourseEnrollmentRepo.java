package com.example.AcademicWebApp.Repositories;

import com.example.AcademicWebApp.Models.OptionalCourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OptionalCourseEnrollmentRepo extends JpaRepository<OptionalCourseEnrollment, Integer> {

    @Query("from optional_course_enrollment where username=?1 order by preference")
    public List<OptionalCourseEnrollment> getAllByUsername(String username);

}
