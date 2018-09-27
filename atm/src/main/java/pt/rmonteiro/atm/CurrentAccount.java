package pt.rmonteiro.atm;

import java.math.BigDecimal;

/**
 * Week 3 exercise of TDD course. 
 * 
 * https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/3
 *
 * The user currrent account
 *  
 * @author ruimonteiro
 */
public class CurrentAccount {
	
	private String number;
	private BigDecimal balance;
	private String code;
	
	/**
	 * The constructor
	 * 
	 * @param number the account number
	 * @param balance the account balance
	 * @param code the code to access the account
	 */
	public CurrentAccount(final String number, final BigDecimal balance, String code) {
		super();
		this.number = number;
		this.balance = balance;
		this.code = code;
	}
	
	/**
	 * @return the account number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @return the account balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * @return the account code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Decrements the account balance
	 * @param value the amount to decrement
	 */
	public void decrementBalance(final BigDecimal value) {
		this.balance = this.balance.subtract(value);
	}
	
	/**
	 * Increments the account balance
	 * @param value the amount to increment
	 */
	public void incrementBalance(final BigDecimal value) {
		this.balance = this.balance.add(value);
	}
}
