package tn.esprit.spring.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import tn.esprit.spring.entities.Answer;
import tn.esprit.spring.entities.Quiz;
import tn.esprit.spring.entities.QuizQuestion;
import tn.esprit.spring.service.courses.CourseServiceImpl;
import tn.esprit.spring.service.courses.QuizServiceImpl;
import tn.esprit.spring.serviceInterface.courses.UserCourseService;

@RestController
@Api(tags = "Quiz Management")
@RequestMapping("/quiz")
public class QuizRestController {
	@Autowired
	CourseServiceImpl courseService;
	@Autowired
	UserCourseService userCourseService;
	@Autowired
	QuizServiceImpl quizService;
	@PostMapping(path = "createQuiz/{courseId}")
	public void createQuiz(@RequestBody Quiz q,@PathVariable("courseId")Long courseId) {
		
		courseService.createQuizz(q, courseId);
		
	}
	@PostMapping(path = "addQuestion/{quizId}")
	public void addQuestion(@RequestBody QuizQuestion quest,@PathVariable("quizId")Long quizId) {
		
		quizService.addQuestionToQuiz(quest, quizId);
		
	}
	@PostMapping(path = "addQuestions/{quizId}")
	public void addQuestions(@RequestBody Set<QuizQuestion> quest,@PathVariable("quizId")Long quizId) {
		
		quizService.addListQuestionsToQuiz(quest, quizId);
		
	}
	@DeleteMapping(path="deleteQuestion/{questionId}/{quizId}")
	public void deleteQuestion(@PathVariable("questionId")Long questionId,@PathVariable("quizId")Long quizId) {
	   quizService.removeQuestion(questionId,quizId);
		
	}
	@PutMapping(path="editQuestion")
	public void editQuestion(@RequestBody QuizQuestion q) {
		quizService.editQuestion(q);
	}
	@PostMapping(path = "addAnswers/{questionId}")
	public void addAnswers(@RequestBody Set<Answer> answer,@PathVariable("questionId")Long questionId) {
		
		quizService.addAnswersToQuestion(answer, questionId);
		
	}
	@PutMapping(path="setCorrectAnswer/{answerId}")
	public void setCorrectAnswer(@PathVariable("answerId")Long answerId) {
		quizService.setCorrectAnswer(answerId) ;
	}
	
	
	
	
	
	

}
