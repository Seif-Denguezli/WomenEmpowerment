package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Service;
@Repository
public interface serviceRepo extends JpaRepository<Service, Long> {
	@Query(nativeQuery = true,value = "SELECT s.service_id ,s.start_date,s.end_date , s.job from service s  WHERE s.job like %?1%")
	public List<tn.esprit.spring.entities.Service> recherche(String keyword);
}
