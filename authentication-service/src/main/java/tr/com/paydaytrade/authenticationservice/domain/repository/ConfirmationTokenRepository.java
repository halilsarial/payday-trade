package tr.com.paydaytrade.authenticationservice.domain.repository;

import org.springframework.data.repository.CrudRepository;
import tr.com.paydaytrade.authenticationservice.domain.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
