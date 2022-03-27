package tn.esprit.spring.websocket;

import tn.esprit.spring.entities.Message;
import tn.esprit.spring.entities.ResponseMessage;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.MessageRepo;
import tn.esprit.spring.repository.UserRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {
@Autowired
MessageRepo messageRepo;
@Autowired
UserRepository userRepo;

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    public void notifyFrontend(final String message) {
        ResponseMessage response = new ResponseMessage(message);
        notificationService.sendGlobalNotification();

        messagingTemplate.convertAndSend("/topic/messages", response);
   
    }

    public void notifyUser( Long sender,String reciver , Message message) {
        ResponseMessage response = new ResponseMessage(message.getMessageContent());
        User senderU = userRepo.findById(sender).orElse(null);
        Long id_reciver = Long.parseLong(reciver);
        User reciverU = userRepo.findById(id_reciver).orElse(null);
        
        message.setReciver(reciverU);
        message.setSender(senderU);
       // message.setDateEnvoie(Date.valueOf(LocalDate.now()));
        messageRepo.save(message);
        
        notificationService.sendPrivateNotification(reciver);
        messagingTemplate.convertAndSendToUser(reciver, "/topic/private-messages", response);
    }
    
    public List<String> getConversation(Long user1,Long user2){
    	
    	return messageRepo.getconversation(user1, user2);
    	
    }
} 
