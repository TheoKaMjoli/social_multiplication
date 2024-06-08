package microservices.book.social_multiplication.controller;

import microservices.book.social_multiplication.domain.MultiplicationResultAttempt;
import microservices.book.social_multiplication.service.serviceInt.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
final class multiplicationResultsAttemptConroller {

    private final MultiplicationService multiplicationService;

    @Autowired
    multiplicationResultsAttemptConroller(final MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    //We will implement our post later

    //    @RequiredArgsConstructor
    //    @NoArgsConstructor(force = true)
    //    @Getter
    //    private static final class ResultResponse{
    //        private final boolean correct;
    //    }

    @PostMapping
    ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt multiplicationResultAttempt){

        boolean isCorrect = multiplicationService.checkAttempts(multiplicationResultAttempt);
        MultiplicationResultAttempt attemptCopy = new
                MultiplicationResultAttempt(multiplicationResultAttempt.getUser(),
                                            multiplicationResultAttempt.getMultiplication(),
                                            multiplicationResultAttempt.getResultAttempt(),
                                            isCorrect);

        return ResponseEntity.ok(attemptCopy);
    }

    @GetMapping
    ResponseEntity<List<MultiplicationResultAttempt>> getStatistics(@RequestParam("alias") String alias){
        return ResponseEntity.ok(multiplicationService.getStatsForUser(alias));
    }

}
