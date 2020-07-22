package com.goms.application.service.data;

import java.util.Set;

public class ProfileData {
    private Integer id;
    private String name;
    Set<String> privileges;

    public Integer getId() {
        return id;
    }

    public ProfileData setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProfileData setName(String name) {
        this.name = name;
        return this;
    }

    public Set<String> getPrivileges() {
        return privileges;
    }

    public ProfileData setPrivileges(Set<String> privileges) {
        this.privileges = privileges;
        return this;
    }
}
