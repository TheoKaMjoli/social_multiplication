package microservices.book.social_multiplication.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Identifies the attempt from a {@link User} to solve a
 * {@link Multiplication}.
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class MultiplicationResultAttempt {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private final User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MULTIPLICATION_ID")
    private final Multiplication multiplication;
    private final int resultAttempt;
    private final boolean correct;

    //empty for (de) serilization
    MultiplicationResultAttempt(){
        user = null;
        multiplication = null;
        resultAttempt = -1;
        correct = false;
    }



}
