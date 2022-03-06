package tn.esprit.spring.service.event;

import tn.esprit.spring.entities.*;

public interface TransactionService {
	public void addTransaction(Transaction transaction) ;
	public Transaction editTransaction(Transaction transaction);
	public void removeTransaction(Long IdTransaction);
	public void getAllTransaction();
	
}
