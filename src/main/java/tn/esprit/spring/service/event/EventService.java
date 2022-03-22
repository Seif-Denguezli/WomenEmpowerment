package tn.esprit.spring.service.event;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.SmsRequest;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.enumerations.EventType;

public interface EventService  {

	
	public void removeEvent(Long IdEvent);
	public List<Event> Get_all_Event();
	public Long findUserDonationsById(Long id);
	public void Participer_event(Long userid, Long eventId);
	public void cancelparticipation(Long userid, Long eventId);
	public void AcceptInvitation(Long userid , boolean  a);
	public String TargetAtrbut(Long eventId);
	public Event affecte_place_event_byavie(Long id_event);
	public  void invite_participants (Event t) throws MessagingException;
	//	public void createEventbyUser(Long idUser , Event event)  throws MessagingException;
	//public List<Event> findEventsWithSorting(String field);
	void sendSms(SmsRequest smsRequest, String nb, String msg);
	
	
	
	public  ResponseEntity<?> createEventbyUser(Long idUser ,MultipartFile multipartFile, 
			String EventName,String description,Date createAt , Date endAt , 
			EventType typeEvent , int maxPlace , 
			float targetDonation , String place)  throws MessagingException,IOException;
	
	public Event EditEventCreateByUser(Event event , Long idEvent);
	public ResponseEntity<?> updateImageForEvent(Long idmedia, MultipartFile multipartFile) throws IOException ;
	  public ResponseEntity<?> deleteImageForEvent(@PathVariable("idMedia") Long id)throws IOException;
	  public ResponseEntity<?> addImageForDocumentOfEvent(Long idEvent,Set<MultipartFile> multipartFile) throws IOException;
	
	//---------------pagination-------------//
	 public Page<Event> findEventWithPaginationAndSorting(int offset,int pageSize,String field);
	       
	   
	
	
	
}
