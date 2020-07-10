package com.goms.interfaces.usermanagement.facade;


import com.goms.application.shared.ApplicationException;
import com.goms.interfaces.usermanagement.facade.dto.LoginRequest;
import com.goms.interfaces.usermanagement.facade.dto.LoginResponse;

public interface UserManagementFacade {

    LoginResponse generateToken(LoginRequest authRequest) throws ApplicationException;

    Integer validateAndExtractUserIdFromToken(String token) throws ApplicationException;

}
