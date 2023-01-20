package com.example.AcademicWebApp.Models;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OptionalCourseData {

    private String faculty;
    private Integer year;


}
