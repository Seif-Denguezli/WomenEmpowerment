package tn.esprit.spring.service.courses;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Quiz;
import tn.esprit.spring.entities.QuizQuestion;
import tn.esprit.spring.repository.QuestionRepository;
import tn.esprit.spring.repository.QuizzRepository;
import tn.esprit.spring.serviceInterface.courses.QuizService;
@Service
public class QuizServiceImpl implements QuizService {
@Autowired
QuizzRepository quizzRepository;
@Autowired
QuestionRepository questionRepository;
	@Override
	public void addQuestionToQuiz(QuizQuestion q, Long quizId) {
		questionRepository.save(q);
		Quiz quiz = quizzRepository.findById(quizId).get();
		Set<QuizQuestion> quest = new HashSet<>();
		quest.add(q);
		quiz.setQuestions(quest);
		quizzRepository.flush();
		

	}

	@Override
	public void addListQuestionsToQuiz(Set<QuizQuestion> questions, Long quizId) {
		questionRepository.saveAll(questions);
		Quiz quiz = quizzRepository.findById(quizId).get();
		quiz.setQuestions(questions);
		quizzRepository.save(quiz);
		

	}

	@Override
	public void removeQuestion(Long questionId,Long quizId) {
		Quiz q = quizzRepository.findById(quizId).get();
		QuizQuestion qq = questionRepository.findById(questionId).get();
		
		q.getQuestions().remove(qq);
		quizzRepository.flush();
		questionRepository.deleteById(questionId);
		
	}

	@Override
	public void editQuestion(QuizQuestion question) {
		questionRepository.saveAndFlush(question);		
	}
	

}
