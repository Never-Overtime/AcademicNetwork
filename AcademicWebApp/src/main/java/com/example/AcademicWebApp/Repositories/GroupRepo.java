package com.example.AcademicWebApp.Repositories;

import com.example.AcademicWebApp.Models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupRepo extends JpaRepository<Group, String>
{
    @Query("Select g.gid from group g where g.faculty=?1 and g.year=?2")
    List<Integer> findAllGidsByFacultyAndYear(Integer faculty, Integer year);



}