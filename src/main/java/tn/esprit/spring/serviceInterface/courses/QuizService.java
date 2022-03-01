package tn.esprit.spring.serviceInterface.courses;

import java.util.Set;

import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.QuizQuestion;
@Service
public interface QuizService {
public void addQuestionToQuiz(QuizQuestion q, Long quizId);
public void addListQuestionsToQuiz(Set<QuizQuestion>questions,Long quizId);
public void removeQuestion(Long questionId,Long quizId);
public void editQuestion(QuizQuestion question);
}
