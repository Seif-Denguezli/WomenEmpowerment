package tn.esprit.spring.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.http.HttpResponse;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import tn.esprit.spring.repository.CertificateRepository;
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
	@PutMapping(path="editQuestion/{questionId}")
	public void editQuestion(@RequestBody QuizQuestion q,@PathVariable("questionId")Long questionId) {
		quizService.editQuestion(q,questionId);
	}
	@PostMapping(path = "addAnswers/{questionId}")
	public void addAnswers(@RequestBody Set<Answer> answer,@PathVariable("questionId")Long questionId) {
		
		quizService.addAnswersToQuestion(answer, questionId);
		
	}
	@PutMapping(path="setCorrectAnswer/{answerId}")
	public void setCorrectAnswer(@PathVariable("answerId")Long answerId) {
		quizService.setCorrectAnswer(answerId) ;
	}
	@PostMapping(path="answerQuestion/{idUser}/{idAnswer}")
	public Answer userAnswerQuestion(@PathVariable("idUser")Long idUser,@PathVariable("idAnswer")Long idAnswer) {
		return quizService.answerQuizQuestion(idUser, idAnswer);
	}
	@GetMapping(path="calculUserScore/{userId}/{quizId}")
	public int userScoreCalcul(@PathVariable("userId")Long userId,@PathVariable("quizId")Long quizId) {
		return quizService.calculScore(userId, quizId);
		
	}
	@GetMapping(path="calculUserTotScore/{idUser}/{idCourse}")
	public int userCourseScore(@PathVariable("idUser")Long idUser,@PathVariable("idCourse") Long idCourse) {
		return quizService.userCourseScore(idUser, idCourse);
	}
	@GetMapping(path="didUserPass/{idUser}/{idCourse}")
	int userPassed(@PathVariable("idUser")Long idUser,@PathVariable("idCourse") Long idCourse) {
		return quizService.userPassed(idUser, idCourse);
	}
	@Autowired
	ServletContext context;
	@PostMapping(path="genCertificateQR",produces = MediaType.IMAGE_PNG_VALUE)
	@ResponseBody
	public byte[] userAnswerQuestion(HttpServletResponse response) throws IOException, InterruptedException  {
		response.setContentType("image/png");
		
		 byte[] qr =  quizService.createCertificateQr(null);
		 
		
		 return qr;
		 
		
		
		
	}

	
	

}
