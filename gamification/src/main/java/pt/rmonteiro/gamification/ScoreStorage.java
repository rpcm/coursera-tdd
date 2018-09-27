package pt.rmonteiro.gamification;

import java.util.Set;

/**
 * Week 4 exercise of TDD course. 
 * 
 * https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/4
 * 
 * The interface score storage
 * 
 * @author ruimonteiro
 */
public interface ScoreStorage {

	/**
	 * @param user the user
	 * @param total the total score
	 * @param type the score type
	 * @return the saved score
	 */
	Score saveScore(String user, int total, ScoreType type);
	
	/**
	 * @param user the user
	 * @param type the score type
	 * @return the total score for the given user and score type
	 */
	int getTotalScore(String user, ScoreType type);

	/**
	 * @return the users names with scores
	 */
	Set<String> getUsersWithScores();

	/**
	 * @return the score types with scores
	 */
	Set<ScoreType> getScoreTypesWithScore();
}
