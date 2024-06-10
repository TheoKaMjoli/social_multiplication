package microservices.book.social_multiplication.controller;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.social_multiplication.domain.Multiplication;
import microservices.book.social_multiplication.domain.MultiplicationResultAttempt;
import microservices.book.social_multiplication.domain.ResultResponse;
import microservices.book.social_multiplication.domain.User;
import microservices.book.social_multiplication.service.serviceInt.MultiplicationService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.RequestBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import static java.nio.file.Paths.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;


@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationResultAttemptControllerTest.class)
public class MultiplicationResultAttemptControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;

    @Autowired
    private MockMvc mvc;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<MultiplicationResultAttempt> jsonResult;
    private JacksonTester<ResultResponse> jsonResponse;
    private JacksonTester<List<MultiplicationResultAttempt>>jsonResultsAttemptList;

    @Before
    public void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void postResultReturnCorrect() throws Exception {
        genericParametrizedTest(true);
    }

    @Test
    public void postResultReturnNotCorrect() throws Exception{
        genericParametrizedTest(false);
    }

    @Test
    void genericParametrizedTest(final boolean correct) throws Exception {
        // given (remember we're not testing here the service itself)
        given(multiplicationService.checkAttempts(any(MultiplicationResultAttempt.class))).willReturn(correct);
        User user = new User("John");
        Multiplication multiplication = new Multiplication(50,20);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, false);

        //when
        MockHttpServletResponse response = mvc.perform(
                (RequestBuilder) post("/results").
                        contentType(MediaType.APPLICATION_JSON).
                            contentType(MediaType.valueOf(jsonResult.write(attempt).getJson()))).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResult.write(
                                                                                new MultiplicationResultAttempt(
                                                                                        attempt.getUser(),
                                                                                        attempt.getMultiplication(),
                                                                                        attempt.getResultAttempt(),
                                                                                        correct)).getJson()
                                                                        );
    }

    @Test
    public void getUserStats() throws IOException {
        //given
        User user = new User("Johnny Depp");
        Multiplication multiplication = new Multiplication(50, 70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, true);
        List<MultiplicationResultAttempt>recentAttempts = Lists.newArrayList(attempt, attempt);
        given(multiplicationService.getStatsForUser("Johnny Depp")).willReturn(recentAttempts);

        //when
        MockHttpServletResponse response = mvc.perform(get("/results").param("alias", "Johnny_Depp")).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResultsAttemptList.write(recentAttempts).getJson());

    }


}