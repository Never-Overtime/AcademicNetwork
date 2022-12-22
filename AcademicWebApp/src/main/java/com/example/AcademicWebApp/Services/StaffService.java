package com.example.AcademicWebApp.Services;

import com.example.AcademicWebApp.Controllers.RestAPI.Entities.*;
import com.example.AcademicWebApp.Models.Faculty;
import com.example.AcademicWebApp.Models.Grade;
import com.example.AcademicWebApp.Models.Group;
import com.example.AcademicWebApp.Models.StudentEnrollment;
import com.example.AcademicWebApp.Repositories.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Service
public class StaffService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private GradeRepo gradeRepo;

    @Autowired
    private FacultyRepo facultyRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private UserDataRepo userDataRepo;

    @Autowired
    private StudentEnrollmentRepo studentEnrollmentRepo;

    public List<FacultyGroups> getFacultiesWithGroups() {
        List<Faculty> faculties = facultyRepo.findAll();
        List<FacultyGroups> facultyGroups = new ArrayList<>();

        for (Faculty faculty : faculties) {
            List<Group> groups = groupRepo.findAllByFaculty(faculty.getFid());

            FacultyGroups facultyGroup = new FacultyGroups(faculty.getName(), groups);
            facultyGroups.add(facultyGroup);
        }

        return facultyGroups;
    }

    private double calculateAverageGrade(StudentEnrollment s) {
        List<Grade> grades = gradeRepo.findAllByUsernameAndFaculty(s.getUsername(), s.getFid());

        double avg = 0;

        for (Grade grade: grades) {
            avg += (double) grade.getGradevalue();
        }
        avg /= grades.size();

        return avg;
    }

    private int averageGradeComparator(StudentEnrollment s1, StudentEnrollment s2) {
        double avg1 = calculateAverageGrade(s1);
        double avg2 = calculateAverageGrade(s2);

        if (avg1 == avg2) {
            return 0;
        }
        else if (avg1 < avg2) {
            return 1;
        }
        else{
            return -1;
        }
    }

//    public List<Student> getStudentsFromGroup(Group group) {
//        List<Student> students = new ArrayList<>();
//
//        List<Student> fromGroup1 = studentRepo.findAllByGroup1(group.getGid());
//        List<Student> fromGroup2 = studentRepo.findAllByGroup2(group.getGid());
//
//        students.addAll(fromGroup1);
//        students.addAll(fromGroup2);
//
//        System.out.println(students);
//
//        students.sort(this::averageGradeComparator);
//
//        System.out.println(students);
//
//        return students;
//    }
//
//    public List<FacultyYears> getFacultiesWithYears() {
//        List<FacultyYears> facultyYears = new ArrayList<>();
//        List<Faculty> faculties = facultyRepo.findAll();
//
//        for (Faculty faculty: faculties){
//            FacultyYears facultyYears1 = new FacultyYears();
//            facultyYears1.setFacultyName(faculty.getName());
//
//            List<Integer> years = new ArrayList<>();
//            for (int i = 1; i <= faculty.getNoyears(); i++) {
//                years.add(i);
//            }
//            facultyYears1.setYears(years);
//
//            facultyYears.add(facultyYears1);
//        }
//
//        return facultyYears;
//    }
//
//    public List<Student> getStudentsFromFacultyYear(FacultyYear facultyYear) {
//        List<Student> students = new ArrayList<>();
//
//         List<StudentEnrollment> studentEnrollments = studentEnrollmentRepo.findAllByFidAndYear(
//                facultyRepo.findFidByName(facultyYear.getFacultyName()), facultyYear.getYear());
//
//        for (StudentEnrollment studentEnrollment: studentEnrollments) {
//            students.add(studentRepo.findById(studentEnrollment.getUsername()).get());
//        }
//
//        students.sort(this::averageGradeComparator);
//
//        return students;
//    }

    public List<StudentAverage> getStudentsFromFacultyYearGroup(StaffFilter staffFilter) {
        List<StudentAverage> students = new ArrayList<>();

        String faculty = staffFilter.getFaculty();
        String sYear = staffFilter.getYear();
        String sGroup = staffFilter.getGroup();

        int year = -1, group = -1;
        if (sYear != "")
            year = Integer.parseInt(sYear);
        if (sGroup != "")
            group = Integer.parseInt(sGroup);

        List<StudentEnrollment> filteredStudents = studentEnrollmentRepo.findAllByFacultyYearGroup(faculty, year, group);

        filteredStudents.sort(this::averageGradeComparator);

        for (StudentEnrollment student: filteredStudents) {
            students.add(new StudentAverage(
                    student.getUsername(),
                    userDataRepo.findAllByUsername(student.getUsername()).getName() + " " + userDataRepo.findAllByUsername(student.getUsername()).getSurname(),
                    student.getYear(),
                    groupRepo.findByUsernameFidYear(student.getUsername(), student.getFid(), student.getYear()).getGid(),
                    calculateAverageGrade(student),
                    studentRepo.findByUsername(student.getUsername()).getScholarship()
            ));
        }

        return students;
    }

    public List<Integer> getGroupsByFaculty(FacultyYear facultyYear) {
        List<Integer> groups = new ArrayList<>();

        String faculty = facultyYear.getFaculty();
        String sYear = facultyYear.getYear();

        int year = -1;
        if (sYear != "")
            year = Integer.parseInt(sYear);

        List<Group> groupList = groupRepo.findAllByYearAndFaculty(faculty, year);

        for (Group gr: groupList) {
            groups.add(gr.getGid());
        }

        return groups;
    }

    public Integer setScholarships(FoundingData foundingData) {
        int minimumGrade = foundingData.getMinimumGrade();
        int moneyPerPerson = foundingData.getMoneyPerPerson();

        List<StudentEnrollment> studentEnrollments = studentEnrollmentRepo.findAll();
        studentEnrollments.sort(this::averageGradeComparator);

        int totalFounding = 0;

        for (StudentEnrollment se: studentEnrollments) {
            double grade = calculateAverageGrade(se);
            if (grade > minimumGrade) {
                studentRepo.setScholarship(se.getUsername(), moneyPerPerson);
                totalFounding += moneyPerPerson;
            }
        }

        return totalFounding;
    }

    public List<StudentGradeStaff> getStudentFounding() {
        List<StudentGradeStaff> studentGrades = new ArrayList<>();

        List<StudentEnrollment> studentEnrollments = studentEnrollmentRepo.findAll();
        studentEnrollments.sort(this::averageGradeComparator);

        for (StudentEnrollment se: studentEnrollments) {
            double grade = calculateAverageGrade(se);
            studentGrades.add(new StudentGradeStaff(
                    se.getUsername(),
                    grade
            ));
        }

        return studentGrades;
    }

}
