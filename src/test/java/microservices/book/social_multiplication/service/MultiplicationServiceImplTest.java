package microservices.book.social_multiplication.service;

import microservices.book.social_multiplication.domain.Multiplication;
import microservices.book.social_multiplication.domain.MultiplicationResultAttempt;
import microservices.book.social_multiplication.domain.User;
import microservices.book.social_multiplication.repository.MultiplicationResultAttemptRepo;
import microservices.book.social_multiplication.repository.UserRepo;
import microservices.book.social_multiplication.service.serviceImpl.MultiplicationServiceImpl;
import microservices.book.social_multiplication.service.serviceInt.RandomGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


public class MultiplicationServiceImplTest {

    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private MultiplicationResultAttemptRepo attemptRepo;

    @Mock
    private UserRepo userRepo;

    @Before
    public void setUp(){

        //With this call to initMocks we tell mockito to process the annotations
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService, attemptRepo, userRepo);
    }

    @Test
    public void createRandomMultiplicationTest(){
        //given (our mocked Random Generator service will return first 50, then 30)
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        //when
        Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

        //assert
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
        assertThat(multiplication.getResult()).isEqualTo(1500);
    }

    @Test
    public void checkCorrectAttemptTest(){
        //given
        Multiplication multiplication = new Multiplication(50,60);
        User user = new User("Jack_Black");
        MultiplicationResultAttempt attempt  = new MultiplicationResultAttempt(user, multiplication, 3000, false);
        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);

        given(userRepo.findByAlias("Jack_Black")).willReturn(Optional.empty());

        //when
        boolean attemptResult = multiplicationServiceImpl.checkAttempts(attempt);
        //then
        assertThat(attemptResult).isTrue();
        verify(attemptRepo).save(verifiedAttempt);
    }

    @Test
    public void CheckIncorrectAttemptTest(){

        //given
        Multiplication multiplication = new Multiplication(50,60);
        User user = new User("Johnny_Depp");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);

        given(userRepo.findByAlias("Johnny_Depp")).willReturn(Optional.empty());
        //when
        boolean attemptResult = multiplicationServiceImpl.checkAttempts(attempt);

        //then
        assertThat(attemptResult).isFalse();
        verify(attemptRepo).save(attempt);

    }
}
