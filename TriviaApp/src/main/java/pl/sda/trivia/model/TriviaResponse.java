package pl.sda.trivia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TriviaResponse {

    @JsonProperty("response_code")
    private int responseCode;
    private List<Quiz> results;
}
