package tn.esprit.spring.service.courses;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sun.xml.bind.v2.runtime.reflect.Lister.CollectionLister;

import tn.esprit.spring.entities.Certificate;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Quiz;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.CourseRepository;
import tn.esprit.spring.repository.QuizzRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.serviceInterface.courses.CourseService;
@Service
public class CourseServiceImpl implements CourseService {
@Autowired
CourseRepository courseRepository;
@Autowired
UserRepository userRepository;
@Autowired
QuizzRepository quizzRepository; 
	@Override
	public Course addCourse(Course c) {
		return courseRepository.save(c);
	}
	@Override
	public Course editCourse(Course c,Long courseId) {
		Course course = courseRepository.findById(courseId).get();
		
		course.setCourseName(c.getCourseName());
		course.setEndDate(c.getEndDate());
		course.setNbHours(c.getNbHours());
		course.setStartDate(c.getStartDate());
		course.setOnGoing(c.isOnGoing());
		courseRepository.flush();
		
		return c;
	}

	@Override
	public void affectCourseToUser(Long idUser, Course c) {
		if(courseVerificator(idUser)==true) {
		courseRepository.save(c);
		User usr = userRepository.findById(idUser).get();
		Set<Course> course = new HashSet<>();
		course.add(c);
		usr.setCreatedCourses(course);
		userRepository.flush();
		}
		else {
		System.out.println("cant create course 2 courses allready created");
	}
	}

	@Override
	public Course deleteCourse(Long idUser, Long idCourse) {
		    User usr = userRepository.findById(idUser).get();
			Course c = courseRepository.findById(idCourse).orElse(null);
			usr.getCreatedCourses().remove(c);
			courseRepository.delete(c);
			return c ;
		
	}
	@Override
	public void createQuizz(Quiz Q, Long idCourse) {
		Course c = courseRepository.findById(idCourse).get();
		Set<Quiz> quiz = new HashSet<>();
		quiz.add(Q);
		c.getQuiz().add(Q);
	
		courseRepository.flush();
		quizzRepository.save(Q);
		
	}
	@Override
	public List<Course> displayAllCourses() {
		return courseRepository.findAll();
	}
	@Override
	public Course displayCourse(Long courseId) {
		return courseRepository.findById(courseId).get();
	}
	@Override
	public List<User> getAllParticipants(Long courseId) {
		Course course = courseRepository.findById(courseId).get();
		List<User> users = new ArrayList<>();
		Set<Certificate> c = course.getCertificates();
		for (Certificate certificate : c) {
			users.add(certificate.getUser());
		}
		return users;
	}
	@Override
	public User getParticipant(Long courseId) {
		Long id = courseRepository.findUserById(courseId);
		if(id==null) {
			return null;
		}
		else {
			return userRepository.findById(id).get();
		}
		
		
		
	}
	
	@Override
	@Scheduled(cron= "0 0 0 * * *")
	public String coursesStatus() {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		  Date date = new Date(); 
		  
		List<Course> courses = courseRepository.findAll();
		
		for (Course course : courses) {
			if(course.getStartDate().toString().equals(formatter.format(date))) {
				course.setOnGoing(true);
				courseRepository.save(course);
			}
			
		}
		
		
		return null;
	}
	@Override
	public boolean courseVerificator(Long userId) {
		int counter=0;
		User user = userRepository.findById(userId).get();
		Set<Course> userCourses = user.getCreatedCourses();
		for (Course course : userCourses) {
			if(course.isOnGoing()==true) {
				counter = counter +1 ;
			}
		    
		}
		if(counter < 2) return true;
		else return false;
	}
	
	
	
	
	
	
	
	
	
}
