package pl.sda.trivia;

import pl.sda.trivia.api.Category;
import pl.sda.trivia.api.Difficulty;
import pl.sda.trivia.api.Type;
import pl.sda.trivia.error.DataFormatException;
import pl.sda.trivia.error.DataNotAvailableException;
import pl.sda.trivia.model.QuizToComplete;
import pl.sda.trivia.repository.*;
import pl.sda.trivia.service.QuizService;
import pl.sda.trivia.service.TriviaQuizService;
import java.util.*;

public class TriviaAppConsole {

    private static final QuizRepository quizRepo = new TriviaAPIQuizRepository();
    //wstrzyknięcie zależności
    private static final QuizCompleteRepositoryJpa quizCompleteRepository = new QuizCompleteRepositoryJpa();
    private static final QuizService quizService = new TriviaQuizService(quizRepo, quizCompleteRepository);
    private static final CategoryRepository categoryRepo = new TriviaCategoryRepository();
    private static final Scanner scanner = new Scanner(System.in);
    private static Set<QuizToComplete> quizzes;
    private static List<Category> categories = categoryRepo.findAll();
    public static void main(String[] args) {
        //TODO: dopisz fragment, w którym użytkownik może wybierać kategorię : jedna z listy kategorii lub dowolna.
        //poziom trudności: łatwy, średni, trudny lub dowolny
        //typ pytań: wielokrtony, prawda/fałsz lub dowolny
        // liczbę pytań od 1 do 50
        //TODO: dopisz możliwość powtarzania całego cyklu
        TriviaAppConsole console = new TriviaAppConsole();

        console.getQuizzesWithResultsAndRepeatChoice();
    }

    private void getQuizzesWithResultsAndRepeatChoice() {
        setQuizzesParametersByUser();
        List<QuizToComplete> list = getQuizToComplete();
        int points = quizService.evaluateQuizSet(new HashSet<>(list));
        System.out.println("Zdobyłeś punktów: " + points);
        showQuizzesResult(list);
        System.out.println("Czy chcesz kontynuować i rozpocząć nowy quiz? " +
                "Wybierz Y żeby kontynuować lub wciśnij dowolny klawisz żeby wyjść");
        String userChoice = scanner.nextLine().toUpperCase();
        if (userChoice.equals("Y")) {
            getQuizzesWithResultsAndRepeatChoice();
        }
    }

    private List<QuizToComplete> getQuizToComplete() {
        List<QuizToComplete> list = new ArrayList<>(quizzes);
        int currentQuiz = 0;
        exit:
        while (true) {
            QuizToComplete quizToComplete = list.get(currentQuiz);
            screenTemplate(quizToComplete);
            String userChoice = scanner.nextLine().toUpperCase();
            switch (userChoice) {
                case "1":
                    if (quizToComplete.getOptions().size() > 0) {
                        quizToComplete.setAnswer(quizToComplete.getOptions().get(0));
                    }
                    break;
                case "2":
                    if (quizToComplete.getOptions().size() > 1) {
                        quizToComplete.setAnswer(quizToComplete.getOptions().get(1));
                    }
                    break;
                case "3":
                    if (quizToComplete.getOptions().size() > 2) {
                        quizToComplete.setAnswer(quizToComplete.getOptions().get(2));
                    }
                    break;
                case "4":
                    if (quizToComplete.getOptions().size() > 3) {
                        quizToComplete.setAnswer(quizToComplete.getOptions().get(3));
                    }
                    break;
                case "N":
                    if (currentQuiz < list.size() - 1)
                        currentQuiz++;
                    break;
                case "P":
                    if (currentQuiz > 0) {
                        currentQuiz--;
                    }
                    break;
                case "K":
                    break exit;
            }
        }
        return list;
    }

    public Set<QuizToComplete> setQuizzesParametersByUser() {
        try {
            quizzes = quizService.findQuizSet(
                    setAmountOfQuestionsByUser(),
                    setDifficultyByUser(),
                    setTypeByUser(),
                    setCategoryByUser()
            );
        } catch (DataNotAvailableException e) {
            System.out.println(e.getMessage());
            System.out.println("Aplikacja nie może działać. Sprawdź dostęp do danych");
        } catch (DataFormatException e) {
            System.out.println(e.getMessage());
        }
        return quizzes;
    }

    public void screenTemplate(QuizToComplete quiz){
        System.out.println(quiz.getQuestion());
//        System.out.println("-".repeat(quiz.getQuestion().length()));
        for (int i = 0; i < quiz.getQuestion().length(); i++) {
            System.out.print("-");
        }
        System.out.println();
        int counter = 0;
        for (var option : quiz.getOptions()) {
            counter++;
            char chosenAnswer = ' ';
            if(option.equals(quiz.getAnswer())){
                chosenAnswer = 'x';
            }
            System.out.println(counter + ". [" + chosenAnswer + "] " + option);
        }
        System.out.println("Menu: odpowiedź: 1, 2, 3, 4 lub 1, 2; następne pytanie: N, poprzednie pytanie : P, koniec: K");
    }

    public void showQuizzesResult(List<QuizToComplete> quizzes) {
        for (var quiz : quizzes) {
            System.out.println(quiz.getQuestion());
//        System.out.println("-".repeat(quiz.getQuestion().length()));
            for (int i = 0; i < quiz.getQuestion().length(); i++) {
                System.out.print("-");
            }
            System.out.println();
            int counter = 1;
            for (var option : quiz.getOptions()) {
                if (option.equals(quiz.getAnswer()) && option.equals(quiz.getCorrectAnswer())) {
                    System.out.println(counter + ". [x] " + option);
                } else if (option.equals(quiz.getAnswer())){
                    System.out.println(counter + ". [-] " + option);
                } else if (option.equals(quiz.getCorrectAnswer())) {
                    System.out.println(counter + ". [*] " + option);
                } else {
                    System.out.println(counter + ". [ ]" + option);
                }
                counter++;
            }
        }
    }

    public Type setTypeByUser() {
        while (true){
            System.out.println("Wybierz typ pytań: MULTI - wybór z wielu, T/F - prawda/fałsz, R - dowolny.");
            String userChoice = scanner.nextLine().toUpperCase();
            if (userChoice.equals("MULTI")) {
                return Type.MULTIPLE_CHOICE;
            } else if (userChoice.equals("T/F")) {
                return Type.TRUE_FALSE;
            } else if (userChoice.equals("R")) {
                return Type.ANY;
            } else {
                System.out.println("Wybierz jedną z dostępnych opcji");
            }
        }
    }

    public Difficulty setDifficultyByUser(){
        scanner.nextLine();
        while(true) {
            System.out.println("Wybierz stopień trudności: E - łatwy, M - średni, H - trudny, R - dowolny");
            String userChoice = scanner.nextLine().toUpperCase();
            if (userChoice.equals("E")){
                return Difficulty.EASY;
            } else if (userChoice.equals("M")) {
                return Difficulty.MEDIUM;
            } else if (userChoice.equals("H")) {
                return Difficulty.HARD;
            } else if (userChoice.equals("R")) {
                return Difficulty.ANY;
            } else {
                System.out.println("Wybierz jedną z dostępnych opcji");
            }
        }
    }

    public Category setCategoryByUser() {
        while (true) {
            Random random = new Random();
            System.out.println("Wybierz kategorię za pomocą cyfr od 1 do "
                    + categories.size() + " lub " + (categories.size() + 1) + " - dowolna");
            int userChoice = scanner.nextInt();
            scanner.nextLine();
            if (userChoice >= 1 && userChoice <= categories.size()) {
                return categories.get(userChoice-1);
            } else if (userChoice == (categories.size()+1)) {
                return categories.get(random.nextInt(categories.size()));
            } else {
                System.out.println("Wybierz jedną z dostępnych opcji");
            }
        }
    }

    public int setAmountOfQuestionsByUser(){
        int userChoice;
        while (true) {
            System.out.println("Podaj liczbę pytań od 1 do 50");
            userChoice = scanner.nextInt();
            if (userChoice >= 1 && userChoice <= 50) {
                return userChoice;
            }
            else {
                System.out.println("Liczba musi mieścić się w przedziale od 1 do 50");
            }
        }
    }
}
