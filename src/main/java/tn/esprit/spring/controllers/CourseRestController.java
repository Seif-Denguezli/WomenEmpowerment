package tn.esprit.spring.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import tn.esprit.spring.entities.User;
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

@PutMapping(path="editCourse/{courseId}")
public void editCourse(@RequestBody Course c,@PathVariable("courseId")Long courseId) {
	courseService.editCourse(c,courseId);

}
@GetMapping(path="getAllCourses")
public List<Course> getAllCourses(){
	return courseService.displayAllCourses();
}
@GetMapping(path="getCourse/{courseId}")
public Course getCourse(@PathVariable("courseId")Long courseId){
	return courseService.displayCourse(courseId);
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
/*@GetMapping(path="getParticipants/{courseId}")
public List<User> getParticipants(@PathVariable("courseId")Long courseId){
	quizService.participantPassed(courseId);
	return courseService.getAllParticipants(courseId);
}*/
@GetMapping(path="getParticipant/{userId}")
public User getParticipant(@PathVariable("userId")Long userId){
	return courseService.getParticipant(userId);
}
@GetMapping(path="verifyUserjoin/{userId}/{courseId}")
public int verificate(@PathVariable("userId")Long userId,@PathVariable("courseId")Long courseId) {
	return courseService.userjoinCourseVerificator(userId, courseId);
}


}
