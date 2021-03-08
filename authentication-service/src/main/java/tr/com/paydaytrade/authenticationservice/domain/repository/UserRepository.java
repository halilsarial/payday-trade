package tr.com.paydaytrade.authenticationservice.domain.repository;

import org.springframework.data.repository.CrudRepository;
import tr.com.paydaytrade.authenticationservice.domain.model.AppUser;

public interface UserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByUserName(String userName);
}
