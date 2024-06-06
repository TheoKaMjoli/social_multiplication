package microservices.book.social_multiplication.repository;

import microservices.book.social_multiplication.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

public interface MultiplicationRepo extends CrudRepository<Multiplication, Long> {



}
