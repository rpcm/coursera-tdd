package pt.rmonteiro.camelCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Week 1 exercise of TDD course. 
 * 
 * @see https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/1 
 * 
 * @author ruimonteiro
 */
public class CamelCase {
	
	private static final String START_WITH_NUMBERS_REGEX = "^[0-9].*$";
	private static final String SPECIAL_CHARACTERS = "\\!\"#$%&()*+-,./:;<=>?@\\[\\]{|}~";
	private static final String CAMEL_CASE_REGEX = "(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])|(?=[A-Z][a-z])|(?<=\\d)(?=\\D)|(?=\\d)(?<=\\D)";
	static final String START_WITH_NUMBERS_MESSAGE_ERROR = "não deve começar com números";
	static final String SPECIAL_CHARACTERS_MESSAGE_ERROR = "caracteres especiais não são permitidos, somente letras e números";
	
	/**
	 * @param original the input word
	 * @return a list of words as the result of splitting the input word by CamelCase
	 */
	public static List<String> converterCamelCase(String original) {
		if(original == null || original.isEmpty() || original.trim().isEmpty()) {
			return new ArrayList<>();
		}
		validateInputFormat(original);
		return getCamelCaseWords(original);
	}
	
	/**
	 * Validates the input word
	 * 
	 * @param input the input
	 */
	private static void validateInputFormat(final String input) {
		if(input.matches(START_WITH_NUMBERS_REGEX)) {
			throw new InvalidInputFormat(START_WITH_NUMBERS_MESSAGE_ERROR);
		}
		for (Character character : SPECIAL_CHARACTERS.toCharArray()) {
			if(input.contains(character.toString())) {
				throw new InvalidInputFormat(SPECIAL_CHARACTERS_MESSAGE_ERROR);
			}
		}
	}
	
	/**
	 * @param input the input word
	 * @return  a list of words as the result of splitting the input word by CamelCase
	 */
	private static List<String> getCamelCaseWords(final String input) {
		final String[] words = splitWordsByCamelCase(input);
		final List<String> result = new ArrayList<>();
		for (String word : words) {
			result.add(capitalizeWord(word));
		}
		return result;
	}
	
	/**
	 * @param input the input word
	 * @return an array of words as the result of splitting the input word by CamelCase
	 */
	private static String[] splitWordsByCamelCase(final String input) {
		return input.split(CAMEL_CASE_REGEX);
	}
	
	/**
	 * @param input the input word
	 * @return the input word in LowerCase if it has one character in LowerCase. Otherwise, returns the input word without changes
	 */
	private static String capitalizeWord(final String input) {
		for (Character character : input.toCharArray()) {
			if(Character.isLowerCase(character)) {
				return input.toLowerCase();
			}
		}
		return input;
	}
}
