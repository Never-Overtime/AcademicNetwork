package com.example.AcademicWebApp.Repositories;

import com.example.AcademicWebApp.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourseRepo extends JpaRepository<Course, Integer>
{
    //DONE TODO actually getCoursesForStudentXGroup has to be modified - only return courses with priority 1 and 3! then use that for the last TODO
    @Query("from course where fid=?1 and year=?2 and (priority=1 or priority=3)")
    List<Course> findCoursesByFidAndYear(Integer fid, Integer year);

    @Query("from course where fid=?1 and year=?2 and priority=2")
    List<Course> findOptionalsByFidAndYear(Integer fid, Integer year);
}