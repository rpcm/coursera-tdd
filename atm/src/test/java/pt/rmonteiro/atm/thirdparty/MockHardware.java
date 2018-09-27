package pt.rmonteiro.atm.thirdparty;

import pt.rmonteiro.atm.thirdparty.Hardware;

/**
 * Week 3 exercise of TDD course. 
 * 
 * @see https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/3
 * 
 * The mock of the hardware
 *  
 * @author ruimonteiro
 */
public class MockHardware implements Hardware {

	private String accountNumber;
	
	/**
	 * @see pt.rmonteiro.atm.thirdparty.Hardware#getAccountNumberFromCard()
	 */
	@Override
	public String getAccountNumberFromCard() {
		return accountNumber;
	}
	
	/**
	 * @see pt.rmonteiro.atm.thirdparty.Hardware#getMoney()
	 */
	@Override
	public void getMoney() {
		//NOOP
	}

	/**
	 * @see pt.rmonteiro.atm.thirdparty.Hardware#readEnvelope()
	 */
	@Override
	public void readEnvelope() {
		//NOOP
	}
	
	/**
	 * Adds an account number
	 * @param accountNumber the account numbber to be added
	 */
	public void addAccountNumber(final String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
