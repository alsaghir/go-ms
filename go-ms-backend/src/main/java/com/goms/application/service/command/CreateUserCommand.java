package com.goms.application.service.command;

public class CreateUserCommand {
    private String email;
    private String firstName;
    private String lastName;

    public String getEmail() {
        return email;
    }

    public CreateUserCommand setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public CreateUserCommand setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CreateUserCommand setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
