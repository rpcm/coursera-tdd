package pt.rmonteiro.camelCase;

/**
 * Week 1 exercise of TDD course. 
 * 
 * @see https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/1 
 * 
 * Thrown to indicate an invalid input format
 * 
 * @author ruimonteiro
 */
public class InvalidInputFormat extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Var-arg constructor
	 * 
	 * @param message the error message
	 */
	public InvalidInputFormat(String message) {
        super(message);
    }
}
