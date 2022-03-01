package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.User;
@Repository
public interface DonationRepo  extends JpaRepository<Donation , Long>{

}
