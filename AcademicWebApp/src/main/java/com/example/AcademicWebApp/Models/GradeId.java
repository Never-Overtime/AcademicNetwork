package com.example.AcademicWebApp.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class GradeId implements Serializable {

    private String username;

    private Integer cid;

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        GradeId gradeId = (GradeId) obj;

        return Objects.equals(gradeId.getCid(), this.getCid()) && Objects.equals(gradeId.getUsername(), this.getUsername());

    }
}
