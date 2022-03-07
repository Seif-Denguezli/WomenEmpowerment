package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Certificate;
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
	@Query(nativeQuery = true,value="SELECT * FROM `certificate` WHERE certificate.course_course_id=:param")
public Certificate findByCourse(@Param("param") Long courseId);
}
