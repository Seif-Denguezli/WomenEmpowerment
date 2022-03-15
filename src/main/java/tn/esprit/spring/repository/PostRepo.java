package tn.esprit.spring.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.*;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
	@Query(value="SELECT DATEDIFF(NOW(),:d  )   ", nativeQuery=true)
	public int diffrence_entre_date(@Param("d") Date d);
}
