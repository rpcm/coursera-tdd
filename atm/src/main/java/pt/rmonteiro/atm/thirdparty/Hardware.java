package pt.rmonteiro.atm.thirdparty;

/**
 * Week 3 exercise of TDD course. 
 * 
 * @see https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/3
 *
 * The hardware interface
 *  
 * @author ruimonteiro
 */
public interface Hardware {

	/**
	 * @return the account number corresponding to the given card
	 */
	String getAccountNumberFromCard();
	
	/**
	 * Gives the money to the user
	 */
	void getMoney();
	
	/**
	 * Reads the envelope provided by the user
	 */
	void readEnvelope();
}
