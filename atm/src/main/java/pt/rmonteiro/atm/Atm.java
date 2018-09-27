package pt.rmonteiro.atm;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import pt.rmonteiro.atm.service.RemoteService;
import pt.rmonteiro.atm.thirdparty.Hardware;

/**
 * Week 3 exercise of TDD course. 
 * 
 * https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/3
 * 
 * The interface ATM
 * 
 * @author ruimonteiro
 */
public class Atm {
	
	static final String LOGIN_ERROR = "Não foi possível autenticar o usuário";
	static final String LOGIN_SUCCESS = "Usuário Autenticado";
	static final String WITHDRAW_MONEY = "Retire seu dinheiro";
	static final String INSUFFICIENT_BALANCE = "Saldo insuficiente";
	static final String DEPOSIT_SUCCESSFUL = "Depósito recebido com sucesso";
	static final String BALANCE_MESSAGE = "O saldo é ";
	
	private Hardware hardware;
	private RemoteService remoteService;
	private CurrentAccount currentAccount = null;
	private Locale locale;
	
	/**
	 * Public constructor
	 * @param locale the locale
	 */
	public Atm(final Locale locale) {
		this.locale = locale;
	}
	
	/**
	 * Do the login with the access code
	 * @param code the account access code
	 * @return the message to display to the user
	 */
	public String doLogin(final String code) {
		final String accountNumber = hardware.getAccountNumberFromCard();
		return login(accountNumber, code);
	}
	
	/**
	 * @return the balance of the current account
	 */
	public String getBalance() {
		return formatBalance(currentAccount.getBalance());
	}
	
	/**
	 * Gets the requested amount of money
	 * @param value the amount of money to get
	 * @return the message to display to the user
	 */
	public String getMoney(final BigDecimal value) {
		if(currentAccount.getBalance().compareTo(value) > 0) {
			currentAccount.decrementBalance(value);
			remoteService.saveCurrentAccount(currentAccount);
			hardware.getMoney();
			return WITHDRAW_MONEY;
		}
		return INSUFFICIENT_BALANCE;
	}
	
	/**
	 * Deposit the requested amount of money
	 * @param value the amount of money to deposit
	 * @return the message to display to the user
	 */
	public String depositMoney(final BigDecimal value) {
		currentAccount.incrementBalance(value);
		remoteService.saveCurrentAccount(currentAccount);
		hardware.readEnvelope();
		return DEPOSIT_SUCCESSFUL;
	}
	
	/**
	 * Setterfor the hardware
	 * @param hardware the hardware
	 */
	public void setHardware(final Hardware hardware) {
		this.hardware = hardware;
	}

	/**
	 * Setter for the remote service
	 * @param remoteService the remote service
	 */
	public void setRemoteService(final RemoteService remoteService) {
		this.remoteService = remoteService;
	}

	/**
	 * Do the login with the access code and the account number
	 * @param accountNumber the account number
	 * @param code the acess code
	 * @return the message to display to the user
	 */
	private String login(final String accountNumber, final String code) {
		if(accountNumber == null || accountNumber.trim().isEmpty() ||code == null || code.trim().isEmpty()) {
			return LOGIN_ERROR;
		}
		this.currentAccount = remoteService.recoveryCurrentAccount(accountNumber, code);
		return this.currentAccount == null ? LOGIN_ERROR : LOGIN_SUCCESS;
	}
	
	/**
	 * @param balance the balance
	 * @return the formatted balance according to the locale
	 */
	private String formatBalance(final BigDecimal balance) {
		final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);
		final StringBuilder stringBuilder = new StringBuilder(BALANCE_MESSAGE).
												append(numberFormat.format(balance.doubleValue()).replaceAll("\\s",""));
		return stringBuilder.toString(); 
	}
}
