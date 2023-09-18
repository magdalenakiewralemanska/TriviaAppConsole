package pl.sda.trivia.service;

import pl.sda.trivia.api.Category;
import pl.sda.trivia.api.Difficulty;
import pl.sda.trivia.api.Type;
import pl.sda.trivia.entity.QuizComplete;
import pl.sda.trivia.model.QuizToComplete;
import pl.sda.trivia.repository.QuizCompleteRepositoryJpa;
import pl.sda.trivia.repository.QuizRepository;

import java.util.*;
import java.util.stream.Collectors;

public class TriviaQuizService implements QuizService{

    private final QuizRepository quizRepository;
    private final QuizCompleteRepositoryJpa quizCompleteRepository;

    public TriviaQuizService(QuizRepository quizRepository, QuizCompleteRepositoryJpa quizCompleteRepository) {
        this.quizRepository = quizRepository;
        this.quizCompleteRepository = quizCompleteRepository;
    }

    @Override
    public Set<QuizToComplete> findQuizSet(int amount, Difficulty difficulty, Type type, Category category) {
        return quizRepository.findQuizByDifficultyAndTypeAndCategory(amount, difficulty, type, category)
                .stream()
                .map(quiz -> {
                    List<String> options = new ArrayList<>(quiz.getIncorrectAnswers());
                    options.add(quiz.getCorrectAnswer());
                    Collections.shuffle(options);
                    return QuizToComplete.builder()
                            .question(quiz.getQuestion())
                            .correctAnswer(quiz.getCorrectAnswer())
                            .options(options)
                            .build();
                    }
                ).collect(Collectors.toSet());
    }

    @Override
    public int evaluateQuizSet(Set<QuizToComplete> quizzes) {
        quizzes.stream()
                .map(q -> {
                            QuizComplete quizComplete = new QuizComplete();
                            quizComplete.setAnswer(q.getAnswer());
                            quizComplete.setQuestion(q.getQuestion());
                            quizComplete.setCorrectAnswer(Objects.equals(q.getAnswer(), q.getCorrectAnswer()));
                            return quizComplete;
                        })
                .forEach(quizCompleteRepository::save);
//        return (int)quizzes
//        .stream()
//        .filter(quiz -> quiz.getAnswer().equals(quiz.getCorrectAnswer()))
//        .count();
        int counter = 0;
        for (QuizToComplete question: quizzes) {
            if (question.getCorrectAnswer().equals(question.getAnswer())){
                counter++;
            }
        }
        return counter;
    }
}
