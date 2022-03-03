package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Course;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
