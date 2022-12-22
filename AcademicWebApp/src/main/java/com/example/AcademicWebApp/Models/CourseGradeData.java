package com.example.AcademicWebApp.Models;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseGradeData {
    String course;
    int grade;
}
