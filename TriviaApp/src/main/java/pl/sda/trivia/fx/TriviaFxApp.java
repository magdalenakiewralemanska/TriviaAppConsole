package pl.sda.trivia.fx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.sda.trivia.TriviaAppConsole;
import pl.sda.trivia.api.Difficulty;
import pl.sda.trivia.api.Type;
import pl.sda.trivia.entity.QuizComplete;
import pl.sda.trivia.model.QuizToComplete;
import pl.sda.trivia.repository.*;
import pl.sda.trivia.service.QuizService;
import pl.sda.trivia.service.TriviaQuizService;

import java.util.*;


public class TriviaFxApp extends Application {
    private final TriviaAppConsole console = new TriviaAppConsole();
    private final QuizCompleteRepositoryJpa quizCompleteRepositoryJpa = new QuizCompleteRepositoryJpa();
    private final ToggleGroup group = new ToggleGroup();
    private final VBox root = new VBox();
    private final Scene scene = new Scene(root);
    private final HBox buttons = new HBox();
    private final Button endBtn = new Button("Koniec");
    private QuizRepository quizRepository = new TriviaAPIQuizRepository();
    private final QuizCompleteRepositoryJpa quizCompleteRepository = new QuizCompleteRepositoryJpa();
    private QuizService quizService = new TriviaQuizService(quizRepository, quizCompleteRepository);
    private CategoryRepository categoryRepository = new TriviaCategoryRepository();

    private int quizNumber = 0;
    RadioButton radioButton = new RadioButton("");;
    RadioButton radioButton1 = new RadioButton("");;
    RadioButton radioButton2 = new RadioButton("");;
    RadioButton radioButton3 = new RadioButton("");;
    Set<QuizToComplete> quizSet = quizService.findQuizSet(console.setAmountOfQuestionsByUser(),
            console.setDifficultyByUser(),
            console.setTypeByUser(),
            console.setCategoryByUser());
    List<QuizToComplete> quizList = new ArrayList<>(quizSet);
    private final Label question = new Label("");
    Button prevBtn = new Button("Poprzednie pytanie");
    Button nextBtn = new Button("Następne pytanie");

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {

        root.setSpacing(10);
        root.setPadding(new Insets(10, 5, 10, 5));
        root.setAlignment(Pos.TOP_CENTER);
        updateQuiz();
        setButtons();
        root.getChildren().addAll(question,radioButton, radioButton1, radioButton2, radioButton3, buttons);
        stage.setScene(scene);
        stage.setTitle("Trivia App");
        stage.show();
    }

    private void setUserAnswer(){
        RadioButton selected = (RadioButton) group.getSelectedToggle();
        if(selected != null) {
            quizList.get(quizNumber).setAnswer(selected.getText());
        }
    }
    private void updateQuiz(){
        if(quizNumber == 0){
            prevBtn.setDisable(true);
        } else {
            prevBtn.setDisable(false);
        }
        if(quizNumber == quizList.size() - 1){
            nextBtn.setDisable(true);
        } else {
            nextBtn.setDisable(false);
        }
        question.setText(quizList.get(quizNumber).getQuestion());
        final QuizToComplete quiz = quizList.get(quizNumber);
        radioButton.setText(quiz.getOptions().get(0));
        radioButton1.setText(quiz.getOptions().get(1));
        radioButton2.setText(quiz.getOptions().get(2));
        radioButton3.setText(quiz.getOptions().get(3));
        if(Objects.equals(quiz.getAnswer(), radioButton.getText())){
            radioButton.setSelected(true);
        } else {
            radioButton.setSelected(false);
        }
        if(Objects.equals(quiz.getAnswer(), radioButton1.getText())){
            radioButton1.setSelected(true);
        } else {
            radioButton1.setSelected(false);
        }
        if(Objects.equals(quiz.getAnswer(),radioButton2.getText())){
            radioButton2.setSelected(true);
        } else {
            radioButton2.setSelected(false);
        }
        if(Objects.equals(quiz.getAnswer(), radioButton3.getText())){
            radioButton3.setSelected(true);
        } else {
            radioButton3.setSelected(false);
        }
    }

    private void setButtons(){
        radioButton.setToggleGroup(group);
        radioButton1.setToggleGroup(group);
        radioButton2.setToggleGroup(group);
        radioButton3.setToggleGroup(group);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(10, 0, 10, 0));
        prevBtn.setOnAction(actionEvent -> {
            if (quizNumber > 0) {
                setUserAnswer();
                quizNumber--;
                updateQuiz();
            }
        });
        nextBtn.setOnAction(event -> {
            if(quizNumber < quizList.size() - 1){
                setUserAnswer();
                quizNumber++;
                updateQuiz();
            }
        });
        endBtn.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            int points = quizService.evaluateQuizSet(new HashSet<>(quizList));
            alert.setTitle("Wynik quizu");
            alert.setContentText("Zdobyłes punktów: " + points);
            QuizComplete quizComplete = new QuizComplete();
            quizComplete.setQuestion("test");
            quizComplete.setCorrectAnswer(true);
            quizComplete.setAnswer("test");
            quizCompleteRepository.save(quizComplete);
            alert.show();
        });
        buttons.getChildren().addAll(prevBtn, endBtn, nextBtn);
    }
}
