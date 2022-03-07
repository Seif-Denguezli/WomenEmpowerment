package tn.esprit.spring.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.*;
import tn.esprit.spring.service.event.TransactionService;

@RestController
@RequestMapping("/Transaction")
public class TransactionController {
	@Autowired
	TransactionService transactionService;
	
	@PostMapping("/add-Transaction")
	@ResponseBody
	public void addTransaction(@RequestBody Transaction transaction) {
		transactionService.addTransaction(transaction);
	}
	@PutMapping(path="/editTransaction")
	public void editTransaction(@RequestBody Transaction transaction) {
		transactionService.editTransaction(transaction);

	
}
	
	
	
	
}
