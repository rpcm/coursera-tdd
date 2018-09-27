package pt.rmonteiro.gamification;

/**
 * Week 4 exercise of TDD course. 
 * 
 * https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/4
 * 
 * The score types
 * 
 * @author ruimonteiro
 */
public enum ScoreType {
	COIN("moeda"), 
	STAR("estrela"), 
	TOPIC("tópico"), 
	COMMENT("comentário"), 
	LIKE("curtida"),
	ENERGY("energia");
	
	private String label;
	
	/**
	 * The constructor
	 * @param label the label of the score type
	 */
	ScoreType(final String label) {
		this.label = label;
	}
	
	/**
	 * @return the label of the score type
	 */
	public String getLabel() {
		return label;
	}
}
