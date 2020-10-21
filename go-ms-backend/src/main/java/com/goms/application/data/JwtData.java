package com.goms.application.data;

import org.springframework.hateoas.RepresentationModel;

public class JwtData extends RepresentationModel<JwtData> {

    private String token;

    public String getToken() {
        return token;
    }

    public JwtData setToken(String token) {
        this.token = token;
        return this;
    }
}
