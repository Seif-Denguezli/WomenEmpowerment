package tn.esprit.spring.serviceInterface.courses;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Set;

import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Answer;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Quiz;
import tn.esprit.spring.entities.QuizQuestion;
import tn.esprit.spring.entities.User;
@Service
public interface QuizService {
public void addQuestionToQuiz(QuizQuestion q, Long quizId);
public void addListQuestionsToQuiz(Set<QuizQuestion>questions,Long quizId);
public void removeQuestion(Long questionId,Long quizId);
public void editQuestion(QuizQuestion question,Long questionId);
public void addAnswersToQuestion(Set<Answer> answers,Long questionId);
public void setCorrectAnswer(Long answerId);
public Answer answerQuizQuestion(Long idUser,Long idAnswer);
public int calculScore(Long idUser,Long idQuiz);
public int userCourseScore(Long idUser,Long idCourse);
public int userPassed(Long idUser,Long idCourse);
public void createCertificateQr() throws IOException, InterruptedException;
}
