package com.goms.application.service.command;

import com.goms.domain.model.user.Password;

public class GenerateJwtTokenCommand {
    private String email;
    private String password;
    private boolean passwordEncrypted;

    public GenerateJwtTokenCommand(String email, String password, boolean passwordEncrypted) {
        super();
        this.email = email;
        this.password = password;
        this.passwordEncrypted = passwordEncrypted;
    }

    public String getEmail() {
        return email;
    }

    public GenerateJwtTokenCommand setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public GenerateJwtTokenCommand setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isPasswordEncrypted() {
        return passwordEncrypted;
    }

    public GenerateJwtTokenCommand setPasswordEncrypted(boolean passwordEncrypted) {
        this.passwordEncrypted = passwordEncrypted;
        return this;
    }
}
