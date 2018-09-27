package pt.rmonteiro.atm.service;

import pt.rmonteiro.atm.CurrentAccount;

/**
 * Week 3 exercise of TDD course. 
 * 
 * @see https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/3
 *
 * The remote service interface
 *  
 * @author ruimonteiro
 */
public interface RemoteService {

	/**
	 * @param number the account number
	 * @param code the account code
	 * @return the current account which corresponds to the given number and code
	 */
	CurrentAccount recoveryCurrentAccount(String number, String code);
	
	/**
	 * Saves de current account the the actual state
	 * @param currentAccount the current account
	 */
	void saveCurrentAccount(CurrentAccount currentAccount); 
}
