package pt.rmonteiro.gamification;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Week 4 exercise of TDD course. 
 * 
 * https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/4
 * 
 * The the score domain model
 * 
 * @author ruimonteiro
 */
@Document
public class Score implements Comparable<Score>{

	@Indexed
	private String user;
	private int total;
	private ScoreType type;
	
	/**
	 * The constructor
	 * @param user the user
	 * @param total the total score
	 * @param type the score type
	 */
	@PersistenceConstructor
	public Score(String user, int total, ScoreType type) {
		this.user = user;
		this.total = total;
		this.type = type;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * @return the total score
	 */
	public int getTotal() {
		return total;
	}
	
	/**
	 * @return the score type
	 */
	public ScoreType getType() {
		return type;
	}
	
	/**
	 * Increments the total score
	 * @param value the amount to be incremented to the total score
	 */
	public void incrementTotal(int value) {
		total += value;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + total;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Score other = (Score) obj;
		if (total != other.total)
			return false;
		if (type != other.type)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Score otherScore) {
		return Integer.valueOf(otherScore.getTotal()).compareTo(Integer.valueOf(this.getTotal()));
	}
}
