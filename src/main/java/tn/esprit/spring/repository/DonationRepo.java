package tn.esprit.spring.repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.enumerations.Role;
@Repository
public interface DonationRepo  extends JpaRepository<Donation , Long>{

	 @Query("SELECT u.name , max(d.amount_forEvent),e.eventName FROM User u JOIN u.donations d JOIN d.event e GROUP BY e.eventName ORDER BY d.amount_forEvent DESC  ")
	   public List<Object> bestDonner();
	
	
	
	
	
}
