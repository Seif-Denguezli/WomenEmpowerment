package tn.esprit.spring.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import tn.esprit.spring.entities.APIResponse;
import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Media;
import tn.esprit.spring.entities.SmsRequest;
import tn.esprit.spring.enumerations.EventType;
import tn.esprit.spring.repository.EventRepo;
import tn.esprit.spring.repository.MediaRepo;
import tn.esprit.spring.service.event.EventService;
import tn.esprit.spring.serviceInterface.event.CloudinaryService;
import tn.esprit.spring.serviceInterface.event.MediaService;

@CrossOrigin
@RestController
@RequestMapping("/Event")
public class EventController {
	@Autowired
	EventService eventService;
	
	@Autowired
    CloudinaryService cloudinaryService;
	
	@Autowired
	MediaService mediaService;
	@Autowired
	EventRepo eventRepo;
	
	@GetMapping("/TargetDonationattindre/{idEvent}")
	public String getTargetDonationattinreParEvent(@PathVariable("idEvent")Long idEvent){
		return eventService.TargetAtrbut(idEvent);
	}
	
	

	@PutMapping(path="editEventCreatedByUser/{IdEvent}")
	public void editCourse(@RequestBody Event c,@PathVariable("IdEvent")Long IdEvent) {
		eventService.EditEventCreateByUser(c, IdEvent);

	}
	
	@PutMapping("updateImagesForEventCreateByUser")
	public ResponseEntity<?>  UpdateImageforEventCreateByUser(@RequestParam Long idmedia,@RequestParam MultipartFile multipartFile) throws IOException{
		 return eventService.updateImageForEvent(idmedia, multipartFile);
	}

	  

	@PostMapping(path = "createEventByUser")
	public void createEventByUser(@RequestParam Long userid,@RequestParam MultipartFile multipartFile
			,@RequestParam String EventName
			,@RequestParam String Description
			,@RequestParam  (name ="createAt") @DateTimeFormat(pattern = "dd-MM-yyyy")  Date createAt
			,@RequestParam  (name ="endAt") @DateTimeFormat(pattern = "dd-MM-yyyy")  Date endAt
			,@RequestParam EventType typeEvent
			,@RequestParam  int maxPlace
			,@RequestParam  float targetDonation
			,@RequestParam   String place) throws MessagingException, IOException {
		eventService.createEventbyUser(userid, multipartFile, EventName, Description,createAt, endAt, typeEvent, maxPlace, targetDonation, place);
		
		
	}
	   @DeleteMapping("/deleteImage/{id}")
	    public ResponseEntity<?> delete(@PathVariable("id") Long id)throws IOException {
	         
		   eventService.deleteImageForEvent(id);
	        return new ResponseEntity("imagen eliminada", HttpStatus.OK);
	    }
	   
	   
	   
	@GetMapping("/Get-all-Event")
	public List<Event> Get_all_Event(){
		return eventService.Get_all_Event();
	}
	
	
	@GetMapping("/userAllDonation/{userid}")
	public Long getUSERDonationByID(@PathVariable("userid")Long userid){
		return eventService.findUserDonationsById(userid);
	}
	
	  
	  
	  
	@PutMapping("/userparticipe-event/{userid}/{eventId}")
	public void getUSERDonationByID(@PathVariable("userid")Long userid,@PathVariable("eventId")Long eventId){
		 eventService.Participer_event(userid,eventId);
	}
	 @DeleteMapping("/cancelParticiopation/{iduser}/{idEvent}")
	public void cancelparticipation(@PathVariable("iduser")Long iduser,@PathVariable("idEvent")Long idEvent) {
		 eventService.cancelparticipation(iduser, idEvent);
	 }
	
	
	@PutMapping("/affecte-par-avie/{eventId}")
	public Event affect_par_avie(@PathVariable("eventId")Long eventId){
		return eventService.affecte_place_event_byavie(eventId);
	}
	
	//----------pagination------//
    @GetMapping("/paginationAndSort/{offset}/{pageSize}/{field}")
    private APIResponse<Page<Event>> geteventWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field) {
        Page<Event> productsWithPagination = eventService.findEventWithPaginationAndSorting(offset, pageSize, field);
        return new APIResponse<>(productsWithPagination.getSize(), productsWithPagination);
    }
	
	
	

	
	
	
	

	
	
	
	
	  
}
