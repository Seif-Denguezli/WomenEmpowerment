package tn.esprit.spring.controllers;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;

import javax.imageio.stream.FileImageOutputStream;

import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import tn.esprit.spring.entities.Answer;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.FileInfo;
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

@PostMapping(path="Registerface")
public HttpResponse<String> registerface(@RequestBody byte[] image) throws IOException, InterruptedException{
	
	 
	HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("https://alchera-face-authentication.p.rapidapi.com/v1/face"))
			.header("content-type", "multipart/form-data; boundary=---011000010111000001101001")
			.header("uid", "has")
			.header("X-RapidAPI-Host", "alchera-face-authentication.p.rapidapi.com")
			.header("X-RapidAPI-Key", "b648c42070msh2f1e24111397e42p1155f4jsn864d7705eee5")
			.method("POST", HttpRequest.BodyPublishers.ofString("-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"image\"\r\n\r\n\r\n-----011000010111000001101001--\r\n\r\n"))
			.build();
	
	HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
	System.out.println(response.body());
	return response;

	
}

















}
