package microservices.book.social_multiplication.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "app_user")
public final class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;
    private final String alias;

    //empty constructor for JSON (de) serialization
    protected User(){
        alias = null;
    }
}
