package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.*;

import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Service;
@Repository
public interface serviceRepo extends JpaRepository<Service, Long> {

}
