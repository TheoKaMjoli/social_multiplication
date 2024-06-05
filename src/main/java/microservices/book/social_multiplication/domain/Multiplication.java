package microservices.book.social_multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * This class represents a Multiplication in our application.
 */

//@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class Multiplication {

    //both factors
    private int factorA;
    private int factorB;
    private int result;

    public Multiplication(int factorA, int factorB) {
        this.factorA = factorA;
        this.factorB = factorB;
        this.result = factorA * factorB;
    }


    //Empty constructor for JSON serialization
    public Multiplication(){
      this(0, 0);
    }


}
