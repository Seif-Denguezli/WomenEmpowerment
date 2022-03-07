package tn.esprit.spring.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.User;
@Repository
public interface DonationRepo  extends JpaRepository<Donation , Long>{
/*	String rawQuery ="SELECT user,amount FROM Donation GROUP BY user";
	
	@Query(nativeQuery = true , value = rawQuery)
	List<Donation> findUserByAmount(@Param("param")Long idUser);*/
	
	/*@Query(value = "SELECT d FROM DONATION d GROUP BY d.amount , d.donor")
	Set<Donation> findUserByAmount();
	
	
	@Query(value = "SELECT d FROM DONATION d GROUP BY d.amount , d.event")
	Set<Donation> findUserByevent();
	*/
	

	
	
	
	
	
}
