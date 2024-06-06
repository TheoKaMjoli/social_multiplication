package microservices.book.social_multiplication.repository;

import microservices.book.social_multiplication.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {

    Optional<User> findByAlias(final String alias);
}
