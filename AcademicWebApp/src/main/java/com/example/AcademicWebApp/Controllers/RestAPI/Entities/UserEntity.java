package com.example.AcademicWebApp.Controllers.RestAPI.Entities;


public class UserEntity {

    private String role;

    public UserEntity(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
