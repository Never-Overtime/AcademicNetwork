package com.example.AcademicWebApp.Repositories;

import com.example.AcademicWebApp.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, String>
{

}