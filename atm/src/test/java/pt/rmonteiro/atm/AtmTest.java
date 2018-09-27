package pt.rmonteiro.atm;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Locale;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pt.rmonteiro.atm.service.MockRemoteService;
import pt.rmonteiro.atm.service.MockRemoteServiceWithProblems;
import pt.rmonteiro.atm.thirdparty.MockHardware;
import pt.rmonteiro.atm.thirdparty.MockHardwareWithProblems;

/**
 * Week 3 exercise of TDD course. 
 * 
 * @see https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/3
 * 
 * Unit test for {@link Atm}.
 * 
 * @author ruimonteiro
 */
public class AtmTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private static final String CODE = "1234";
	private static final String ACCOUNT_NUMBER = "12345";
	private static final Locale BR_LOCALE = new Locale("pt", "BR");
	
	private Atm atm; 
	private MockHardware mockHardware;
	private MockRemoteService mockRemoteService;
	
	@Before
	public void init() {
		atm = new Atm(BR_LOCALE);
		mockHardware = new MockHardware();
		mockRemoteService = new MockRemoteService();
	}
	
	/**	
     * Test null account number
     */
	@Test
    public void nullAccountNumber() {
		atm.setHardware(mockHardware);
		
		final String result = atm.doLogin(CODE);
		assertEquals(Atm.LOGIN_ERROR, result);
    }
		
	/**	
     * Test empty account number
     */
	@Test
    public void emptyAccountNumber() {
		mockHardware.addAccountNumber("");
		atm.setHardware(mockHardware);
		
		final String result = atm.doLogin(CODE);
		assertEquals(Atm.LOGIN_ERROR, result);
    }
	
	/**	
     * Test null code
     */
	@Test
    public void nullCode() {
		mockHardware.addAccountNumber(ACCOUNT_NUMBER);
		atm.setHardware(mockHardware);
		
		final String result = atm.doLogin(null);
		assertEquals(Atm.LOGIN_ERROR, result);
    }
		
	/**	
     * Test empty code
     */
	@Test
    public void emptyCode() {
		mockHardware.addAccountNumber(ACCOUNT_NUMBER);
		atm.setHardware(mockHardware);
		
		final String result = atm.doLogin("");
		assertEquals(Atm.LOGIN_ERROR, result);
    }
	
	/**	
     * Test valid code
     */
	@Test
    public void validCode() {
		mockHardware.addAccountNumber(ACCOUNT_NUMBER);
		atm.setHardware(mockHardware);
		
		mockRemoteService.addAccount(new CurrentAccount(ACCOUNT_NUMBER, BigDecimal.ZERO, CODE));
		atm.setRemoteService(mockRemoteService);

		final String result = atm.doLogin(CODE);
		assertEquals(Atm.LOGIN_SUCCESS , result);
    }
	
	/**	
     * Test invalid code
     */
	@Test
    public void invalidCode() {
		mockHardware.addAccountNumber(ACCOUNT_NUMBER);
		atm.setHardware(mockHardware);
		
		mockRemoteService.addAccount(new CurrentAccount(ACCOUNT_NUMBER, BigDecimal.ZERO, CODE));
		atm.setRemoteService(mockRemoteService);

		final String result = atm.doLogin("1111");
		assertEquals(Atm.LOGIN_ERROR , result);
    }
    
	/**	
     * Test hardware with problems on login
     */
    @Test
    public void hardwareWithProblemsOnLogin() {
    	exception.expect(RuntimeException.class);
    	exception.expectMessage(MockHardwareWithProblems.ERROR);	
    	
    	final MockHardwareWithProblems mockHardwareWithProblems = new MockHardwareWithProblems();
    	mockHardwareWithProblems.addAccountNumber(ACCOUNT_NUMBER);
		atm.setHardware(mockHardwareWithProblems);
		
		mockRemoteService.addAccount(new CurrentAccount(ACCOUNT_NUMBER, BigDecimal.ZERO, CODE));
		atm.setRemoteService(mockRemoteService);

		atm.doLogin(CODE);
    }
    
    /**	
     * Test remote service with problems on login
     */
    @Test
    public void remoteServiceWithProblemsOnLogin() {
    	exception.expect(RuntimeException.class);
    	exception.expectMessage(MockRemoteServiceWithProblems.ERROR);	
    	
		mockHardware.addAccountNumber(ACCOUNT_NUMBER);
		atm.setHardware(mockHardware);
		
		final MockRemoteServiceWithProblems mockServicoRemotoWithProblems = new MockRemoteServiceWithProblems();
		mockServicoRemotoWithProblems.addAccount(new CurrentAccount(ACCOUNT_NUMBER, BigDecimal.ZERO, CODE));
		atm.setRemoteService(mockServicoRemotoWithProblems);

		atm.doLogin(CODE);
    }
    
    /**	
     * Test zero account balance
     */
    @Test
    public void acountBalanceZero() {
    	doLogin(BigDecimal.ZERO);
    	
    	final String balance = atm.getBalance();
		assertEquals("O saldo é R$0,00", balance);
    }
    
    /**	
     * Test get money with enough balance
     */
    @Test
    public void getMoneyEnoughBalance() {
    	doLogin(BigDecimal.valueOf(100));
    	
		final String result = atm.getMoney(BigDecimal.valueOf(10));
		assertEquals(Atm.WITHDRAW_MONEY, result);
		mockRemoteService.verifyAccount(ACCOUNT_NUMBER, BigDecimal.valueOf(90));
    }
    
    /**	
     * Test get money with insufficient balance
     */
    @Test
    public void getMoneyInsufficientBalance() {
    	final BigDecimal balance = BigDecimal.valueOf(10);
    	doLogin(balance);
    	
    	final String result = atm.getMoney(BigDecimal.valueOf(100));
		assertEquals(Atm.INSUFFICIENT_BALANCE, result);
		mockRemoteService.verifyAccount(ACCOUNT_NUMBER, balance);
    }
    

    /**	
     * Test get money with problems on the hardware
     */
    @Test
    public void getMoneyHardwareWithProblems() {
    	exception.expect(RuntimeException.class);
    	exception.expectMessage(MockHardwareWithProblems.ERROR);	
    	
    	final MockHardwareWithProblems mockHardwareWithProblems = new MockHardwareWithProblems();
    	mockHardwareWithProblems.addAccountNumber(ACCOUNT_NUMBER);
		atm.setHardware(mockHardwareWithProblems);
    	
		mockRemoteService.addAccount(new CurrentAccount(ACCOUNT_NUMBER, BigDecimal.valueOf(100), CODE));
		atm.setRemoteService(mockRemoteService);
    	
		atm.doLogin(CODE);
		atm.getMoney(BigDecimal.valueOf(10));
    }
    
    /**	
     * Test get money with problems on the remote service
     */
    @Test
    public void getMoneyRemoteServiceWithProblems() {
    	exception.expect(RuntimeException.class);
    	exception.expectMessage(MockRemoteServiceWithProblems.ERROR);	
    	
    	mockHardware.addAccountNumber(ACCOUNT_NUMBER);
		atm.setHardware(mockHardware);
		
		final MockRemoteServiceWithProblems mockServicoRemotoWithProblems = new MockRemoteServiceWithProblems();
		mockServicoRemotoWithProblems.addAccount(new CurrentAccount(ACCOUNT_NUMBER, BigDecimal.valueOf(100), CODE));
		atm.setRemoteService(mockServicoRemotoWithProblems);
    	
		atm.doLogin(CODE);
		atm.getMoney(BigDecimal.valueOf(10));
    }
    
    /**	
     * Test deposit money
     */
    @Test
    public void depositMoney() {
    	doLogin(BigDecimal.valueOf(100));
    	
    	final String result = atm.depositMoney(BigDecimal.valueOf(10));
		assertEquals(Atm.DEPOSIT_SUCCESSFUL, result);
		mockRemoteService.verifyAccount(ACCOUNT_NUMBER, BigDecimal.valueOf(110));
    }
    
    /**	
     * Test deposit money with problems on the hardware
     */
    @Test
    public void deposityMoneyHardwareWithProblems() {
    	exception.expect(RuntimeException.class);
    	exception.expectMessage(MockHardwareWithProblems.ERROR);	
    	
    	final MockHardwareWithProblems mockHardwareWithProblems = new MockHardwareWithProblems();
    	mockHardwareWithProblems.addAccountNumber(ACCOUNT_NUMBER);
		atm.setHardware(mockHardwareWithProblems);
    	
		mockRemoteService.addAccount(new CurrentAccount(ACCOUNT_NUMBER, BigDecimal.valueOf(100), CODE));
		atm.setRemoteService(mockRemoteService);
    	
		atm.doLogin(CODE);
		atm.depositMoney(BigDecimal.valueOf(10));
    }
    
    /**	
     * Test deposit money with problems on the remote service
     */
    @Test
    public void depositarComProblemasNoServicoRemoto() {
    	exception.expect(RuntimeException.class);
    	exception.expectMessage(MockRemoteServiceWithProblems.ERROR);	
    	
    	mockHardware.addAccountNumber(ACCOUNT_NUMBER);
		atm.setHardware(mockHardware);
		
		final MockRemoteServiceWithProblems mockServicoRemotoWithProblems = new MockRemoteServiceWithProblems();
		mockServicoRemotoWithProblems.addAccount(new CurrentAccount(ACCOUNT_NUMBER, BigDecimal.valueOf(100), CODE));
		atm.setRemoteService(mockServicoRemotoWithProblems);
    	
		atm.doLogin(CODE);
		atm.depositMoney(BigDecimal.valueOf(10));
    }
    
    /**
     * Do the login with a predefined account number and code, and with the given balance
     * 
     * @param balance the initial balance
     */
    private void doLogin(final BigDecimal balance) {
		mockHardware.addAccountNumber(ACCOUNT_NUMBER);
		atm.setHardware(mockHardware);
		
		mockRemoteService.addAccount(new CurrentAccount(ACCOUNT_NUMBER, balance, CODE));
		atm.setRemoteService(mockRemoteService);
		
		atm.doLogin(CODE);
    }
}
