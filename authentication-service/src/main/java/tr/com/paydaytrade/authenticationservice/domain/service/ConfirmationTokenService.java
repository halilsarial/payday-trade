package tr.com.paydaytrade.authenticationservice.domain.service;

import tr.com.paydaytrade.authenticationservice.domain.model.ConfirmationToken;
import tr.com.paydaytrade.authenticationservice.exception.BaseException;

public interface ConfirmationTokenService {
    ConfirmationToken findByConfirmationToken(String confirmationToken);

    void createConfirmationToken(ConfirmationToken confirmationToken);
}
