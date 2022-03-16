package tn.esprit.spring.service.event;

import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;



import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.SmsRequest;
import tn.esprit.spring.entities.User;

public interface EventService  {
	public void addEvent(Event event) ;
	public Event editEvent(Event event);
	public void removeEvent(Long IdEvent);
	public void joinEvent(Long idUser, Long idEvent );
	public List<Event> Get_all_Event();
	public Long findUserDonationsById(Long id);
	public void Participer_event(Long userid, Long eventId);
	public String TargetAtrbut(Long eventId);
	public Event affecte_place_event_byavie(Long id_event);
	public  void invite_participants (Event t) throws MessagingException;
	public void createEventbyUser(Long idUser , Event event)  throws MessagingException;
	//public List<Event> findEventsWithSorting(String field);
	void sendSms(SmsRequest smsRequest, String nb, String msg);
	
	//---------------pagination-------------//
	 public Page<Event> findEventWithPaginationAndSorting(int offset,int pageSize,String field);
	       
	   
	
	
	
}
