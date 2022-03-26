package tn.esprit.spring.serviceInterface.courses;

import org.springframework.stereotype.Service;

import tn.esprit.spring.exceptions.CoursesLimitReached;

@Service
public interface UserCourseService {
	public void joinCourse(Long idUser,Long idCourse) throws CoursesLimitReached;
	public void leaveCourse(Long certificateId);

}
