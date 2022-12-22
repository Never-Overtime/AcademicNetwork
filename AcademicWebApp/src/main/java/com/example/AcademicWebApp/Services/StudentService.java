package com.example.AcademicWebApp.Services;
import com.example.AcademicWebApp.Models.Student;
import com.example.AcademicWebApp.Models.StudentData;
import com.example.AcademicWebApp.Repositories.FacultyRepo;
import com.example.AcademicWebApp.Repositories.GroupRepo;
import com.example.AcademicWebApp.Repositories.StudentRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@NoArgsConstructor
@Service("studentService")
public class StudentService {

    @Autowired
    private StudentRepo studentRepository;
    @Autowired
    private FacultyRepo facultyRepo;
    @Autowired
    private GroupRepo groupRepo;

    public Student saveStudent(StudentData student)
    {
        Integer fid1 = facultyRepo.findFidByName(student.getFaculty1());
        Integer year1 = student.getYear1();
        Integer fid2 = facultyRepo.findFidByName(student.getFaculty2());
        Integer year2 = student.getYear2();
        String username = student.getUsername();
        //we must get the groups
        List<Integer> groups1 = groupRepo.findAllGidsByFacultyAndYear(fid1, year1);
        List<Integer> groups2 = groupRepo.findAllGidsByFacultyAndYear(fid2, year2);
        Random rand = new Random();
        Integer group1 = groups1.get(rand.nextInt(groups1.size()));
        Integer group2;
        if(groups2.size() == 0)
            group2 = null;
        else
            group2 = groups2.get(rand.nextInt(groups2.size()));

        Student newS = new Student(username, group1, group2, 0);
        studentRepository.save(newS);
        return newS;

    }


}
