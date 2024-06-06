package microservices.book.social_multiplication.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.social_multiplication.domain.Multiplication;
import microservices.book.social_multiplication.service.serviceInt.MultiplicationService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



import static javax.swing.UIManager.get;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationControllerTest.class)
public class MultiplicationControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    // This object will be magically initialized by the initFields method below.
    private JacksonTester<Multiplication> json;

    @Before
    public void setUp(){
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void runTest() throws Exception {
    //given
        given(multiplicationService.createRandomMultiplication()).willReturn(new Multiplication(70, 20));
    //when
//        MockHttpServletResponse response = mvc.perform(
//                        get("/multiplications/random")
//                                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
    //assert
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.
//                OK.value());
//        assertThat(response.getContentAsString())
//                .isEqualTo(json.write(new Multiplication
//                        (70, 20)).getJson());
    }
}
