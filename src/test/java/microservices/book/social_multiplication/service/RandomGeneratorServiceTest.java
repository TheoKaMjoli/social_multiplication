package microservices.book.social_multiplication.service;

import microservices.book.social_multiplication.service.serviceInt.RandomGeneratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomGeneratorServiceTest {

    @Autowired
    private RandomGeneratorService randomGeneratorService;

    @Test
    public void generatRandomFactorIsBetweenExpectedLimits() throws Exception{


        //when a good sample of randomly generated factor is generated
        List<Integer> randomFactors = IntStream.range(0, 1000).map(i -> randomGeneratorService.generateRandomFactor()).boxed().collect(Collectors.toList());

        //then all of them should be between 11 and 100
        //because we want a middle-complexity calculation
        assertThat(randomFactors).containsOnlyElementsOf(IntStream.range(11, 100).boxed().collect(Collectors.toList()));

    }

}
