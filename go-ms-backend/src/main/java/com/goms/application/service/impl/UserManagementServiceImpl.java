package com.goms.application.service.impl;

import com.goms.application.service.UserManagementService;
import com.goms.application.shared.ApplicationException;
import com.goms.domain.model.config.AppConfig;
import com.goms.domain.model.config.AppConfigRepository;
import com.goms.domain.model.user.User;
import com.goms.domain.model.user.UserRepository;
import com.goms.domain.service.AuthService;
import com.goms.domain.service.CryptoService;
import com.goms.domain.shared.DomainError;
import com.goms.domain.shared.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final AppConfigRepository appConfigRepository;
    private final CryptoService cryptoService;
    private final AuthService authService;

    @Autowired
    public UserManagementServiceImpl(
            UserRepository userRepository,
            AppConfigRepository appConfigRepository,
            CryptoService cryptoService,
            AuthService authService) {
        this.userRepository = userRepository;
        this.appConfigRepository = appConfigRepository;
        this.cryptoService = cryptoService;
        this.authService = authService;
    }

    @Transactional(rollbackFor = ApplicationException.class)
    @Override
    public String generateToken(User user) throws ApplicationException {

        AppConfig appConfig = this.appConfigRepository.findAll();

        try {
            user.assignPassword(user.password().checkStateAndGetRaw(this.cryptoService, appConfig.rsaPrivateKey()));
        } catch (DomainException domainException) {
            throw new ApplicationException(domainException);
        }

        try {
            this.authService.authenticateUsingNameAndPassword(user);
        } catch (DomainException domainException) {
            throw new ApplicationException(domainException);
        }

        Optional<User> storedUser = this.userRepository.findByEmail(user.email());

        if (storedUser.isEmpty())
            throw new ApplicationException(
                    new DomainException(
                            "Email: " + user.email() + " not found", DomainError.UNEXPECTED_ERROR));

        try {
            return storedUser
                    .get()
                    .generateToken(
                            this.cryptoService, appConfig.jwtSigningKey(), appConfig.jwtExpirationTime());

        } catch (DomainException domainException) {
            throw new ApplicationException(domainException);
        }
    }

    @Override
    public Integer verifyTokenAndExtractUserIdFrom(String token) throws ApplicationException {
        AppConfig appConfig = this.appConfigRepository.findAll();
        boolean tokenVerified;
        try {
            tokenVerified = this.cryptoService.verifyToken(token, appConfig.jwtSigningKey());
        } catch (DomainException domainException) {
            throw new ApplicationException(domainException);
        }

        if (!tokenVerified)
            throw new ApplicationException(
                    new DomainException("Unsuccessful token verification", DomainError.UNEXPECTED_ERROR));

        Integer userId;
        try {
            userId = this.cryptoService.getUserIdFrom(token);
        } catch (DomainException domainException) {
            throw new ApplicationException(domainException);
        }

        Optional<User> storedUser = userRepository.findById(userId);

        if (storedUser.isEmpty())
            throw new ApplicationException(
                    new DomainException("UserId: " + userId + " not found", DomainError.UNEXPECTED_ERROR));

        return storedUser.get().id();
    }
}
