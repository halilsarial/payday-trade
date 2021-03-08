package tr.com.paydaytrade.accountservice.repository;

import org.springframework.data.repository.CrudRepository;
import tr.com.paydaytrade.accountservice.model.TraderUser;

import java.util.Optional;

public interface TraderUserRepository extends CrudRepository<TraderUser, Long> {
    Optional<TraderUser> findById(Long id);
}
