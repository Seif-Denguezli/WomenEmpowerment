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


	
	
	
	
	
}
