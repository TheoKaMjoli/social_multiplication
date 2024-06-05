package microservices.book.social_multiplication.service.serviceInt;
import microservices.book.social_multiplication.domain.Multiplication;
import microservices.book.social_multiplication.domain.MultiplicationResultAttempt;

public interface MultiplicationService{

    /**
     * Generates a random {@link Multiplication} object.
     *
     * @return a multiplication of randomly generated numbers
     */
    Multiplication createRandomMultiplication();

    /**
     * @return true if the attempt matches the result of the
     * multiplication, false otherwise.
     */

    boolean checkAttempts(final MultiplicationResultAttempt multiplicationResultAttempt);

}
