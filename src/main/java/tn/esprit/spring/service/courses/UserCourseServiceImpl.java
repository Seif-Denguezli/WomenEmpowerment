package tn.esprit.spring.service.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Certificate;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.exceptions.CoursesLimitReached;
import tn.esprit.spring.repository.CertificateRepository;
import tn.esprit.spring.repository.CourseRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.serviceInterface.courses.UserCourseService;
@Service
public class UserCourseServiceImpl implements UserCourseService {
@Autowired
UserRepository userRepository;
@Autowired
CourseRepository courseRepository;
@Autowired
CertificateRepository certificateRepository;
@Autowired
CourseServiceImpl courseService;
	@Override
	public void joinCourse(Long idUser, Long idCourse) throws CoursesLimitReached {
		
		if(courseService.userjoinCourseVerificator(idUser, idCourse)>=0 && courseService.userjoinCourseVerificator(idUser, idCourse)<6) {
			throw new CoursesLimitReached("You can not join 2 courses with same field in a semester");
		}
		if(certificateRepository.findByCourseAndByUserId(idCourse, idUser)!=null){
			throw new CoursesLimitReached("You allready joined this course");
			
		}
		else {
		User us = userRepository.findById(idUser).get();
		Course course = courseRepository.findById(idCourse).get();
	    Certificate c = new Certificate();
	    c.setUser(us);
	    c.setCourse(course);
	    c.setAquired(false);
	    certificateRepository.save(c);
		}
		
	}
	@Override
	public void leaveCourse(Long certificateId) {
		Certificate c = certificateRepository.findById(certificateId).get();
		c.getUser().getObtainedCertificates().remove(c);
		c.getCourse().getCertificates().remove(c);
		c.setCourse(null);
		c.setUser(null);
		
		certificateRepository.delete(c);
		certificateRepository.saveAndFlush(c);
		

		
	}
	

}
