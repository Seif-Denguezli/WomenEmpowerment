package tn.esprit.spring.service.courses;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sun.xml.bind.v2.runtime.reflect.Lister.CollectionLister;

import tn.esprit.spring.entities.Answer;
import tn.esprit.spring.entities.Certificate;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Quiz;
import tn.esprit.spring.entities.QuizQuestion;
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
		course.setDomain(c.getDomain());
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
		if(users.size()==0) {
			return null;
		}
		else
		{
		return users;
		}
	}
	@Override
	public User getParticipant(Long userId) {
		Long id = courseRepository.findUserById(userId);
		if(id==null) {
			return null;
		}
		else {
			return userRepository.findById(id).get();
		}
		
		
		
	}
	
	@Override
	@Scheduled(cron= "0/10 * * * * *")
	public void coursesStatus() {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		  Date date = new Date(); 
		  
		List<Course> courses = courseRepository.findAll();
		
		for (Course course : courses) {
			if(course.getStartDate().toString().equals(formatter.format(date))) {
				course.setOnGoing(true);
				courseRepository.save(course);
			}
			
		}
		
	}
	@Override
	@Scheduled(cron= "0/10 * * * * *")
	public void coursesEnded() {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		  Date date = new Date(); 
		  
		  
		List<Course> courses = courseRepository.findAll();
		
		for (Course course : courses) {
			if(course.getEndDate().toString().equals(formatter.format(date)) || course.getEndDate().before(date)) {
				course.setOnGoing(false);
				courseRepository.save(course);
				
				
			}
			
		}
		
		
		
		
		
		

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
	@Override
	public int userjoinCourseVerificator(Long userId,Long courseId) {
		int counter=0;
		String date ="";
		Course c = courseRepository.findById(courseId).get();
		List<String> bothdates = new ArrayList<String>() ;
		
		List<String> userJoinedCourses = courseRepository.getUserJoinedCourses(userId,c.getDomain().toString());
		System.err.println(userJoinedCourses);
		if(userJoinedCourses.isEmpty() || userJoinedCourses.size()<2) {
			return 100;
		}
		else {
		for (String userj : userJoinedCourses) {
			String [] dato = userj.split(",");
			date= dato[3];
			bothdates.add(date);
			System.err.println(date);
			
		}
		System.err.println(bothdates);
		  Period diff = diffCalculator(bothdates.get(0), bothdates.get(1));
		  Period diff1 = diffCalculator(bothdates.get(1),c.getStartDate().toString());
			if(diff.getYears()!=0 || diff1.getYears()!=0) {
				return 100;
			}
			else {
			counter= counter + diff.getMonths();
			counter= counter + diff1.getMonths();
			System.err.println(counter);
			
		return counter;
			}
	}
	}
	@Override
	public Period diffCalculator(String date1,String date2) {
		return Period.between(
	            LocalDate.parse(date1).withDayOfMonth(1),
	            LocalDate.parse(date2).withDayOfMonth(1));
	}
	
	
	
	
	
	
	
	
	
}
