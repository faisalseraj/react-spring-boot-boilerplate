package com.kitchensaver.backend.DTO;


// This class is used to get user details when they register
public class UserRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String cell;
    private String office;
    private String role;
    private String password;
    private String confirmPassword;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCell() {
        return cell;
    }

    public String getOffice() {
        return office;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
