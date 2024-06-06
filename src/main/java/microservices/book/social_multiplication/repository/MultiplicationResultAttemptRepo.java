package microservices.book.social_multiplication.repository;

import microservices.book.social_multiplication.domain.MultiplicationResultAttempt;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MultiplicationResultAttemptRepo extends CrudRepository<MultiplicationResultAttempt, Long> {

    /**
     * *@return the latest 5 attempts for a given user, identify by their alias
*/
    List<MultiplicationResultAttempt> findTop5byUserAliasOrderByIdDesc(String userAlias);

}
