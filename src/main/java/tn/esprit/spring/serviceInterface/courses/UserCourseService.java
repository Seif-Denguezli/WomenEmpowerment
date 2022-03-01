package tn.esprit.spring.serviceInterface.courses;

import org.springframework.stereotype.Service;

@Service
public interface UserCourseService {
	public void joinCourse(Long idUser,Long idCourse);
	public void leaveCourse(Long certificateId);

}
