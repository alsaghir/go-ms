package com.goms.interfaces.usermanagement.facade;


import com.goms.application.service.data.UserDetailsData;
import com.goms.application.service.data.UserInfoData;
import com.goms.application.shared.ApplicationException;
import com.goms.infrastructure.auth.UserPrincipal;
import com.goms.interfaces.usermanagement.facade.dto.LoginRequest;
import com.goms.interfaces.usermanagement.facade.dto.LoginResponse;

import java.util.Set;

public interface UserManagementFacade {

    LoginResponse generateToken(LoginRequest authRequest) throws ApplicationException;

    Integer validateAndExtractUserIdFromToken(String token) throws ApplicationException;

    UserDetailsData getUserDetails();

    Set<UserInfoData> getUsersInfo();
}
