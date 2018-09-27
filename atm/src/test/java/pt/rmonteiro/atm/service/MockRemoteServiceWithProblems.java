package pt.rmonteiro.atm.service;

import pt.rmonteiro.atm.CurrentAccount;
import pt.rmonteiro.atm.service.RemoteService;

/**
 * Week 3 exercise of TDD course. 
 * 
 * @see https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/3
 * 
 * The mock of the remote service that simulates problems
 *  
 * @author ruimonteiro
 */
public class MockRemoteServiceWithProblems implements RemoteService {

	public static final String ERROR = "Serviço remoto indisponível";
	
	/**
	 * @see pt.rmonteiro.atm.service.RemoteService#recoveryCurrentAccount(java.lang.String, java.lang.String)
	 */
	@Override
	public CurrentAccount recoveryCurrentAccount(final String number, final String code) {
		throw new RuntimeException(ERROR);
	}

	/**
	 * @see pt.rmonteiro.atm.service.RemoteService#saveCurrentAccount(pt.rmonteiro.atm.CurrentAccount)
	 */
	@Override
	public void saveCurrentAccount(final CurrentAccount currentAccount) {
		throw new RuntimeException(ERROR);
		
	}
	
	/**
	 * Adds a current account
	 * @param currentAccount the current account to be added
	 */
	public void addAccount(final CurrentAccount currentAccount) {
		//NOOP
	}
}
