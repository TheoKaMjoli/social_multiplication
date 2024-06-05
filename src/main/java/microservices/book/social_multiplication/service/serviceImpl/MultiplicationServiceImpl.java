package microservices.book.social_multiplication.service.serviceImpl;

import microservices.book.social_multiplication.domain.Multiplication;
import microservices.book.social_multiplication.domain.MultiplicationResultAttempt;
import microservices.book.social_multiplication.service.serviceInt.MultiplicationService;
import microservices.book.social_multiplication.service.serviceInt.RandomGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private RandomGeneratorService randomGeneratorService;

    @Autowired
    public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService){
        this.randomGeneratorService = randomGeneratorService;
    }

    @Override
    public Multiplication createRandomMultiplication() {
       int factorA = randomGeneratorService.generateRandomFactor();
       int factorB = randomGeneratorService.generateRandomFactor();
       return new Multiplication(factorA, factorB);
    }

    @Override
    public boolean checkAttempts(final MultiplicationResultAttempt resultAttempt) {
        boolean correct = resultAttempt.getResultAttempt() == resultAttempt.getMultiplication().getFactorA()
                        * resultAttempt.getMultiplication().getFactorB();

        Assert.isTrue(!resultAttempt.isCorrect(), "You cannot send attempt marked as correct!");  //avoid heck attempt
        //create a copy, now setting the 'correct' field accordingly
        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(resultAttempt.getUser(), resultAttempt.getMultiplication(), resultAttempt.getResultAttempt(), correct);
        return correct;
    }
}
