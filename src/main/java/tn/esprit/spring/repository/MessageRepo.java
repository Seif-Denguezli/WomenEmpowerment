package tn.esprit.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Message;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long>{
	 @Query(value="SELECT `message_content` FROM `message` WHERE `reciver_user_id` = :user1 AND `sender_user_id` = :user2 ORDER BY `date_envoie`", nativeQuery=true)
	    List<String> getconversation(@Param("user1") Long user1, @Param("user2") Long user2);
	 
	 

}
