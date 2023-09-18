package pl.sda.trivia.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TriviaCategory {

   @JsonProperty("trivia_categories")
    private List<Category> triviaCategories;
}
