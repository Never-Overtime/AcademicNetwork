package com.example.AcademicWebApp.Controllers.RestAPI.Entities;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FoundingData {
    private int minimumGrade;
    private int moneyPerPerson;
}
