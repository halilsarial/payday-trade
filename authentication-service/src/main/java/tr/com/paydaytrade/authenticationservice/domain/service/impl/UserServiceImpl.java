package tr.com.paydaytrade.authenticationservice.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.paydaytrade.authenticationservice.domain.model.AppUser;
import tr.com.paydaytrade.authenticationservice.domain.repository.UserRepository;
import tr.com.paydaytrade.authenticationservice.domain.service.UserService;
import tr.com.paydaytrade.authenticationservice.exception.BaseException;
import tr.com.paydaytrade.authenticationservice.exception.UserAlreadyExistsException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public AppUser findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void createUser(AppUser appUser) throws BaseException {
        if (!isValidPassword(appUser.getPassword())) {
            throw new RuntimeException("Only allow passwords with 6 or more alphanumeric characters");
        }
        if (userRepository.findByUserName(appUser.getUserName()) != null) {
            throw new UserAlreadyExistsException("The user already exists!");
        }
        userRepository.save(appUser);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }
}
