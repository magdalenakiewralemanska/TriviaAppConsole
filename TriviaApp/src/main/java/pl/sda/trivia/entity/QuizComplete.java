package pl.sda.trivia.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class QuizComplete {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

//    private User user;

    @Getter
    @Setter
    private String question;

    @Getter
    @Setter
    private String answer;

    @Getter
    @Setter
    private boolean correctAnswer;
    @CreationTimestamp
    private Timestamp creationTime;
}

    //TODO zrób encję użytkownik, okienko do wpisania użytkownika i zapis informacji kto wypełnił test
//    repozytorium do użytkownika wyciągamy użytkownika i sprawdzamy czy hasło jest poprawne, czy użytkownik istnieje
