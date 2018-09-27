package pt.rmonteiro.atm.thirdparty;

import pt.rmonteiro.atm.thirdparty.Hardware;

/**
 * Week 3 exercise of TDD course. 
 * 
 * @see https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/3
 * 
 * The mock of the hardware that simulates problems
 *  
 * @author ruimonteiro
 */
public class MockHardwareWithProblems implements Hardware {

	public static final String ERROR = "Ocorreu uma falha de funcionamento do hardware.";
	
	/**
	 * @see pt.rmonteiro.atm.thirdparty.Hardware#getAccountNumberFromCard()
	 */
	@Override
	public String getAccountNumberFromCard() {
		throw new RuntimeException(ERROR);
	}
	
	/**
	 * @see pt.rmonteiro.atm.thirdparty.Hardware#getMoney()
	 */
	@Override
	public void getMoney() {
		throw new RuntimeException(ERROR);
	}

	/**
	 * @see pt.rmonteiro.atm.thirdparty.Hardware#readEnvelope()
	 */
	@Override
	public void readEnvelope() {
		throw new RuntimeException(ERROR);
	}
	
	/**
	 * Adds an account number
	 * @param accountNumber the account numbber to be added
	 */
	public void addAccountNumber(final String accountNumber) {
		//NOOP
	}
}
