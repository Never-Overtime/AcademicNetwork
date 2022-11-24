package com.example.AcademicWebApp.Controllers.RestAPIs;


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
