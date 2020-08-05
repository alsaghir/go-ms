package com.goms.application.service.data;

public class UserInfoData {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;

    public UserInfoData(Integer id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public UserInfoData setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserInfoData setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserInfoData setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserInfoData setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
