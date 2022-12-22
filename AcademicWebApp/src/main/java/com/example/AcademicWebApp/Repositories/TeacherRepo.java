package com.example.AcademicWebApp.Repositories;

import com.example.AcademicWebApp.Models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepo extends JpaRepository<Teacher, String> {
}
