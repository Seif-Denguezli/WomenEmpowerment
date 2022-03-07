package tn.esprit.spring.serviceInterface.event;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Transaction;
import tn.esprit.spring.repository.EventRepo;
import tn.esprit.spring.repository.TransactionRepo;
import tn.esprit.spring.service.event.TransactionService;
@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	TransactionRepo transactionRepo;
	
	@Autowired
	EventRepo eventRepo;
	@Override
	public void addTransaction(Transaction transaction) {
		transactionRepo.save(transaction);
		
	}


	@Override
	public Transaction editTransaction(Transaction transaction) {
		transactionRepo.saveAndFlush(transaction);
		return transaction;
	}	

	@Override
	public void removeTransaction(Long IdTransaction) {
		Transaction transaction = transactionRepo.findById(IdTransaction).orElse(null);
		transactionRepo.delete(transaction);
		
	}
	





	@Override
	public Long retrieveMaxEventTransactioned() {
	return eventRepo.retrieveMaxEventTransactioned();
	}







}
