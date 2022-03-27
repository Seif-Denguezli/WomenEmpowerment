package tn.esprit.spring.controllers;

import tn.esprit.spring.entities.Message;
import tn.esprit.spring.entities.ResponseMessage;
import tn.esprit.spring.websocket.WSService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WSController {

    @Autowired
    private WSService service;

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody final Message message) {
        service.notifyFrontend(message.getMessageContent());
    }

    @PostMapping("/send-private-message/{sender}/{reciver}")
    public void sendPrivateMessage(@RequestBody Message message,@PathVariable Long sender,@PathVariable String reciver) {
        service.notifyUser(sender,reciver, message);
    }
    
    @GetMapping("/Get-private-conversation/{sender}/{reciver}")
    public List<String> get_private_converation(@PathVariable Long sender,@PathVariable String reciver) {
        return service.getConversation(sender, sender);
    }
}
