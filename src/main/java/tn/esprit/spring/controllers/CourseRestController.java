package tn.esprit.spring.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import tn.esprit.spring.entities.Answer;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Quiz;
import tn.esprit.spring.entities.QuizQuestion;
import tn.esprit.spring.service.courses.CourseServiceImpl;
import tn.esprit.spring.service.courses.QuizServiceImpl;
import tn.esprit.spring.serviceInterface.courses.UserCourseService;

@RestController
@Api(tags = "Courses Management")
@RequestMapping("/course")
public class CourseRestController {
@Autowired
CourseServiceImpl courseService;
@Autowired
UserCourseService userCourseService;
@Autowired
QuizServiceImpl quizService;
/*******************COURSE*********************/
@PostMapping(path = "addCourse/{userid}")
public Course addCourse(@RequestBody Course c,@PathVariable("userid")Long userId) {
	
	courseService.affectCourseToUser(userId, c);
	return c;
}
@DeleteMapping(path="removeCourse/{userId}/{courseId}")
public void deleteCourse(@PathVariable("userId")Long userId,@PathVariable("courseId")Long courseId) {
	courseService.deleteCourse(userId,courseId);
	
}

@PutMapping(path="editCourse")
public void editCourse(@RequestBody Course c) {
	courseService.editCourse(c);

}
/***************************** USER JOIN COURSE**********************/
@PostMapping(path = "joinCourse/{userid}/{courseid}")
public void joinCourse(@PathVariable("userid")Long userId,@PathVariable("courseid")Long courseId) {
	
	userCourseService.joinCourse(userId, courseId);
	
}
@DeleteMapping(path="leaveCourse/{certificateId}")
public void leaveCourse(@PathVariable("certificateId")Long certificateId) {
   userCourseService.leaveCourse(certificateId);
	
}
/************************QUIZ *************************/
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


}
