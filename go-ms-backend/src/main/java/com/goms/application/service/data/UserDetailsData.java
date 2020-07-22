package com.goms.application.service.data;

import java.util.Set;

public class UserDetailsData {
    private String email;
    private String firstName;
    private String lastName;
    private Set<ProfileData> profileDataSet;

    public String getEmail() {
        return email;
    }

    public UserDetailsData setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDetailsData setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDetailsData setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Set<ProfileData> getProfileDataSet() {
        return profileDataSet;
    }

    public UserDetailsData setProfileDataSet(Set<ProfileData> profileDataSet) {
        this.profileDataSet = profileDataSet;
        return this;
    }
}
