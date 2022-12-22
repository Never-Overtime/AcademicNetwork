package com.example.AcademicWebApp.Services;

import com.example.AcademicWebApp.Models.*;
import com.example.AcademicWebApp.Repositories.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@NoArgsConstructor
@Service("studentService")
public class StudentService {

    @Autowired
    private StudentRepo studentRepository;
    @Autowired
    private FacultyRepo facultyRepo;
    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private OptionalCourseEnrollmentRepo optionalCourseEnrollmentRepo;
    @Autowired
    private GradeRepo gradeRepo;


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

    public List<Faculty> getFaculties()
    {
        List<Faculty> listOfFaculties = facultyRepo.findAll();
        return listOfFaculties;
    }
    //make api to get optionals based on faculty, year
    public List<Course> getOptionalsBasedOnFacultyAndYear(OptionalCourseData data)
    {
        String facultyName = data.getFaculty();
        Integer year = data.getYear();
        Integer fid = facultyRepo.findFidByName(facultyName);
        return courseRepo.findOptionalsByFidAndYear(fid, year);
    }


    //getOptionalsForFirstGroup
    //we initially get the username of the student
    //we get its first  group
    //we get the faculty and the year based on the group
    //we get all courses for that specific group, base on faculty and year
    //this is for the curricullum, but if we add the priority 2 we get the optionals

    public List<Course> getCoursesForFirstGroup(String username)
    {
        Student s = studentRepository.getById(username);
        Integer firstGroup = s.getGroup1();
        Group group = groupRepo.getById(firstGroup);
        Integer fid = group.getFaculty();
        Integer year = group.getYear();
        List<Course> courses = courseRepo.findCoursesByFidAndYear(fid, year);
        return courses;
    }

    public List<Course> getCoursesForSecondGroup(String username)
    {
        Student s = studentRepository.getById(username);
        Integer secondGroup = s.getGroup2();
        if(secondGroup == null)
            return null;
        Group group = groupRepo.getById(secondGroup);

        Integer fid = group.getFaculty();
        Integer year = group.getYear();
        List<Course> courses = courseRepo.findCoursesByFidAndYear(fid, year);
        return courses;
    }

    public List<FacultyAndYearData> getFacultiesAndYears(String username)
    {
        List<FacultyAndYearData> listF = new ArrayList<>();
        Student s = studentRepository.getById(username);
        Integer group1id = s.getGroup1();
        Integer group2id = s.getGroup2();

        Group group1 = groupRepo.getById(group1id);
        Faculty faculty1 = facultyRepo.getById(group1.getFaculty());
        String faculty1name = faculty1.getName();
        int faculty1years = group1.getYear();
        FacultyAndYearData f1data = new FacultyAndYearData(faculty1name, faculty1years);
        listF.add(f1data);

        if(group2id != null)
        {
            Group group2 = groupRepo.getById(group2id);
            Faculty faculty2 = facultyRepo.getById(group2.getFaculty());
            String faculty2name = faculty2.getName();
            int faculty2years = group2.getYear();
            FacultyAndYearData f2data = new FacultyAndYearData(faculty2name, faculty2years);
            listF.add(f2data);
        }

        return listF;

    }

    public List<OptionalCourseEnrollment> sendOptionalsPreferences(List<Course> courses, String username)
    {
        Student s = studentRepository.getById(username);

        int preference = 1;
        for(Course course: courses)
        {
            OptionalCourseEnrollment oc = new OptionalCourseEnrollment(username, course.getCid(), preference);
            preference++;
            optionalCourseEnrollmentRepo.save(oc);
        }

        return optionalCourseEnrollmentRepo.getAllByUsername(username);

    }

    public List<Course> getAllCoursesForWhichEnrolled(String username) {

        List<Course> all = new ArrayList<>(getCoursesForFirstGroup(username));
        List<Course> cgroup2 = getCoursesForSecondGroup(username);
        if(cgroup2 != null)
            all.addAll(cgroup2);
        List<OptionalCourseEnrollment> listOp = optionalCourseEnrollmentRepo.getAllByUsername(username);
        System.out.printf(String.valueOf(listOp));
        if(listOp.size() > 0){
            OptionalCourseEnrollment op = listOp.get(0);
            Integer cid = op.getCid();
            Course opc = courseRepo.findById(cid).get();
            System.out.printf(String.valueOf(opc));
            all.add(opc);
        }
        return all;
    }

    public List<CourseGradeData> getGrades(String username, FacultyAndYearData data) {

        Student s = studentRepository.getById(username);
        List<Grade> grades = gradeRepo.getAllGradesByUsername(username);
        List<CourseGradeData> dataList = new ArrayList<>();
        System.out.printf(data.getName());
        System.out.println(data.getYear());
        for(Grade g: grades)
        {
            int cid = g.getCid();
            Course course = courseRepo.getById(cid);
            int fid = facultyRepo.findFidByName(data.getName());
            if(course.getFid() == fid && course.getYear() == data.getYear())
            {
                dataList.add(new CourseGradeData(course.getName(), g.getGradevalue()));
            }
        }

        return dataList;

    }
    //we get the username,a name, a faculty and a year, eventually 2 faculties
    //firstly -> get the group (gid)
    //we need the fid and the year
    //we get fid by Select fid from faculty where name = facultyName
    //we return a student entity?

}
