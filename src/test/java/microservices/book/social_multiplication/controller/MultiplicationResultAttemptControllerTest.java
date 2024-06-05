package microservices.book.social_multiplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.social_multiplication.domain.Multiplication;
import microservices.book.social_multiplication.domain.MultiplicationResultAttempt;
import microservices.book.social_multiplication.domain.ResultResponse;
import microservices.book.social_multiplication.domain.User;
import microservices.book.social_multiplication.service.serviceInt.MultiplicationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

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
        assertThat(response.getContentAsString()).isEqualTo(jsonResponse.write(new ResultResponse(correct)).getJson());


    }


}