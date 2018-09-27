package pt.rmonteiro.camelCase;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pt.rmonteiro.camelCase.CamelCase;
import pt.rmonteiro.camelCase.InvalidInputFormat;

/**
 * Week 1 exercise of TDD course. 
 * 
 * @see https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/1 
 * 
 * Unit test for {@link CamelCase}.
 * 
 * @author ruimonteiro
 */
public class CamelCaseTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
    /**	
     * Test empty input string
     */
	@Test
    public void emptyInputString() {
        final List<String> result = CamelCase.converterCamelCase("");
        assertTrue(result.isEmpty());
    }
    
    /**
     * Test null input string
     */
	@Test
    public void nullInputString() {
        final List<String> result = CamelCase.converterCamelCase(null);
        assertTrue(result.isEmpty());
    }
    
    /**
     * Test space input string
     */
	@Test
    public void spaceInputString() {
        final List<String> result = CamelCase.converterCamelCase(" ");
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void inputStringStartsWithNumbers() {
    	exception.expect(InvalidInputFormat.class);
    	exception.expectMessage(CamelCase.START_WITH_NUMBERS_MESSAGE_ERROR);	
    	CamelCase.converterCamelCase("10Primeiros");
    }
    
    @Test
    public void inputStringContainsSpecialCharacters() {
    	exception.expect(InvalidInputFormat.class);
    	exception.expectMessage(CamelCase.SPECIAL_CHARACTERS_MESSAGE_ERROR);
    	CamelCase.converterCamelCase("nome#Composto");
    }
    
    /**
     * Test input string one word
     */
    @Test
    public void inputStringOneWord() {
    	final String input = "nome";
		final List<String> result = CamelCase.converterCamelCase(input);
    	assertEquals(1,result.size());
    	assertEquals(input, result.get(0));
    }
    
    /**
     * Test input string one word started by uppercase
     */
    @Test
    public void inputStringOneWordStartedByUpppercase() {
		final List<String> result = CamelCase.converterCamelCase("Nome");
    	assertEquals(1,result.size());
    	assertEquals("nome", result.get(0));
    }
    
    /**
     * Test input string one word in uppercase
     */
    @Test
    public void inputStringOneWordInUppercase() {
    	final String input = "CPF";
		final List<String> result = CamelCase.converterCamelCase(input);
    	assertEquals(1,result.size());
    	assertEquals(input, result.get(0));
    }
    
    /**
     * Test input string two words in lowerCamelCase
     */
    @Test
    public void inputStringTwoWordsInLowerCamelCase() {
		final List<String> result = CamelCase.converterCamelCase("nomeComposto");
    	assertEquals(2,result.size());
    	assertEquals("nome", result.get(0));
    	assertEquals("composto", result.get(1));
    }
    
    /**
     * Test input string two words and numbers
     */
    @Test
    public void inputStringTwoWordsAndNumbers() {
		final List<String> result = CamelCase.converterCamelCase("recupera10Primeiros");
    	assertEquals(3,result.size());
    	assertEquals("recupera", result.get(0));
    	assertEquals("10", result.get(1));
    	assertEquals("primeiros", result.get(2));
    }
    
    /**
     * Test input string two words in upperCamelCase
     */
    @Test
    public void inputStringTwoWordsInUpperCamelCase() {
		final List<String> result = CamelCase.converterCamelCase("NomeComposto");
    	assertEquals(2,result.size());
    	assertEquals("nome", result.get(0));
    	assertEquals("composto", result.get(1));
    }
    
    /**
     * Test input string two words, one in uppercase
     */
    @Test
    public void inputStringTwoWordsOneInUppercase() {
		final List<String> result = CamelCase.converterCamelCase("numeroCPF");
    	assertEquals(2,result.size());
    	assertEquals("numero", result.get(0));
    	assertEquals("CPF", result.get(1));
    }
    
    /**
     * Test input string three words, one in uppercase
     */
    @Test
    public void inputStringThreeWordsOneInUppercase() {
		final List<String> result = CamelCase.converterCamelCase("numeroCPFContribuinte");
    	assertEquals(3,result.size());
    	assertEquals("numero", result.get(0));
    	assertEquals("CPF", result.get(1));
    	assertEquals("contribuinte", result.get(2));
    }
}
