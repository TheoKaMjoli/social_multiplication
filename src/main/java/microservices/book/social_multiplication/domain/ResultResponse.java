package microservices.book.social_multiplication.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public final class ResultResponse {
    private final boolean correct;
}
