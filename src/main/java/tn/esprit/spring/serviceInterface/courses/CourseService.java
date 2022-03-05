package tn.esprit.spring.serviceInterface.courses;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Quiz;
import tn.esprit.spring.entities.User;
@Service
public interface CourseService {
public Course addCourse(Course c);
public void affectCourseToUser(Long idUser,Course c);
public Course deleteCourse(Long idUser,Long idCourse);
public Course editCourse(Course c,Long courseId);
public void createQuizz(Quiz Q, Long idCourse);
public List<Course> displayAllCourses();
public Course displayCourse(Long courseId);
public List<User> getAllParticipants(Long courseId);
public User getParticipant(Long courseId);
public String coursesStatus();
public boolean courseVerificator(Long userId);
}
