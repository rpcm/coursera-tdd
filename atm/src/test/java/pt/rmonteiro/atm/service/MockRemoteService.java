package pt.rmonteiro.atm.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pt.rmonteiro.atm.CurrentAccount;
import pt.rmonteiro.atm.service.RemoteService;

/**
 * Week 3 exercise of TDD course. 
 * 
 * @see https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/3
 *
 * The mock of the remote service
 *  
 * @author ruimonteiro
 */
public class MockRemoteService implements RemoteService {

	private Map<String, CurrentAccount> currentAccounts  = new HashMap<>();
	
	/**
	 * @see pt.rmonteiro.atm.service.RemoteService#recoveryCurrentAccount(java.lang.String, java.lang.String)
	 */
	@Override
	public CurrentAccount recoveryCurrentAccount(final String number, final String code) {
		final CurrentAccount currentAccount = currentAccounts.get(number);
		if(currentAccount == null || !currentAccount.getCode().equals(code)) {
			return null;
		}
		return currentAccount;
	}

	/**
	 * @see pt.rmonteiro.atm.service.RemoteService#saveCurrentAccount(pt.rmonteiro.atm.CurrentAccount)
	 */
	@Override
	public void saveCurrentAccount(final CurrentAccount currrentAccount) {
		currentAccounts.replace(currrentAccount.getNumber(), currrentAccount);
	}
	
	/**
	 * Adds a current account
	 * @param currentAccount the current account to be added
	 */
	public void addAccount(final CurrentAccount currentAccount) {
		currentAccounts.put(currentAccount.getNumber(), currentAccount);
	}
	
	/**
	 * Checks if there is a current account with the given number and balance
	 * @param number the account number
	 * @param expectedBalance the expected account balance
	 */
	public void verifyAccount(final String number, final BigDecimal expectedBalance) {
		final CurrentAccount currentAccount = currentAccounts.get(number);
		assertNotNull(currentAccount);
		assertEquals(expectedBalance, currentAccount.getBalance());
	}
}
