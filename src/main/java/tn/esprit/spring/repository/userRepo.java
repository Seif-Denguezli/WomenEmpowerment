package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.spring.entities.User;

public interface userRepo extends CrudRepository<User, Long> {

}
