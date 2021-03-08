package tr.com.paydaytrade.authenticationservice.domain.service;

import tr.com.paydaytrade.authenticationservice.domain.model.AppUser;
import tr.com.paydaytrade.authenticationservice.exception.BaseException;

public interface UserService {
    AppUser findByUserName(String userName);

    void createUser(AppUser appUser) throws BaseException;
}
