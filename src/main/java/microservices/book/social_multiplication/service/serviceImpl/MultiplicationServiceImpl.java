package microservices.book.social_multiplication.service.serviceImpl;

import jakarta.transaction.Transactional;
import microservices.book.social_multiplication.domain.Multiplication;
import microservices.book.social_multiplication.domain.MultiplicationResultAttempt;
import microservices.book.social_multiplication.domain.User;
import microservices.book.social_multiplication.repository.MultiplicationResultAttemptRepo;
import microservices.book.social_multiplication.repository.UserRepo;
import microservices.book.social_multiplication.service.serviceInt.MultiplicationService;
import microservices.book.social_multiplication.service.serviceInt.RandomGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private RandomGeneratorService randomGeneratorService;
    private MultiplicationResultAttemptRepo attemptRepo;
    private UserRepo userRepo;

    @Autowired
    public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService, MultiplicationResultAttemptRepo attemptRepo, UserRepo userRepo) {
        this.randomGeneratorService = randomGeneratorService;
        this.attemptRepo = attemptRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Multiplication createRandomMultiplication() {
       int factorA = randomGeneratorService.generateRandomFactor();
       int factorB = randomGeneratorService.generateRandomFactor();
       return new Multiplication(factorA, factorB);
    }

    @Transactional
    @Override
    public boolean checkAttempts(final MultiplicationResultAttempt resultAttempt) {

        //let's check if the user already exists for that alias
        Optional<User> user = userRepo.findByAlias(resultAttempt.getUser().getAlias());

        boolean isCorrect = resultAttempt.getResultAttempt() == resultAttempt.getMultiplication().getFactorA()
                        * resultAttempt.getMultiplication().getFactorB();
        Assert.isTrue(!resultAttempt.isCorrect(), "You cannot send attempt marked as correct!");  //avoid heck attempt
        //create a copy, now setting the 'correct' field accordingly
        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(resultAttempt.getUser(), resultAttempt.getMultiplication(), resultAttempt.getResultAttempt(), isCorrect);
        //stores the attempt
        attemptRepo.save(checkedAttempt);
        return isCorrect;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias){
        return attemptRepo.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }

}
