package tn.esprit.spring.serviceInterface.courses;

import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Quiz;
@Service
public interface CourseService {
public Course addCourse(Course c);
public void affectCourseToUser(Long idUser,Course c);
public Course deleteCourse(Long idUser,Long idCourse);
public Course editCourse(Course c);
public void createQuizz(Quiz Q, Long idCourse);
}
