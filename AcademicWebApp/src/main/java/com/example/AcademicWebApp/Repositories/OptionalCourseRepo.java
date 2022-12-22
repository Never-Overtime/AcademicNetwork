package com.example.AcademicWebApp.Repositories;

import com.example.AcademicWebApp.Models.Course;
import com.example.AcademicWebApp.Models.OptionalCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionalCourseRepo extends JpaRepository<OptionalCourse, Integer> {

    @Query("SELECT cour FROM course AS cour INNER JOIN optional_course AS oc ON cour.cid = oc.cid WHERE oc.username=?1 AND cour.year=?2")
    public List<Course> findOptionalCoursesByUsername(String username, Integer year);

    @Query("SELECT cour FROM course AS cour INNER JOIN optional_course AS oc ON cour.cid = oc.cid WHERE cour.year=?1 and cour.fid=?2")
    public List<Course> getAllOptionalsForApproval(Integer year, Integer fid);
}
