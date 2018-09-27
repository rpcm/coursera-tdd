package pt.rmonteiro.gamification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pt.rmonteiro.gamification.ScoreType;

/**
 * Week 4 exercise of TDD course. 
 * 
 * https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/4
 * 
 * The the score board
 * 
 * @author ruimonteiro
 */
@Component
public class ScoreBoard {
	
	private ScoreStorage scoreStorage;

	/**
	 * @param user the user
	 * @param total the total score
	 * @param type the score type
	 * @return the added score
	 */
	public Score addScore(final String user, int total, final ScoreType type) {
		return scoreStorage.saveScore(user, total, type);
	}

	/**
	 * @param user the user
	 * @return the scores of the given user
	 */
	public List<Score> getScores(final String user) {
		final List<Score> scores = new ArrayList<>();
		for (ScoreType type : ScoreType.values()) {
			int totalScore = scoreStorage.getTotalScore(user, type);
			if(totalScore > 0) {
				scores.add(new Score(user, totalScore, type));
			}
		}
		return scores;
	}

	/**
	 * @param type the score type
	 * @return the ranking of the scores for the given score type
	 */
	public List<Score> getRanking(final ScoreType type) {
		final List<Score> scores = new ArrayList<>();
		final Set<String> usersWithScores = scoreStorage.getUsersWithScores();
		for (String user : usersWithScores) {
			int totalScore = scoreStorage.getTotalScore(user, type);
			if(totalScore > 0) {
				scores.add(new Score(user, totalScore, type));
			}
		}
		Collections.sort(scores);
		return scores;
	}
	
	/**
	 * Set´s the score storage
	 * @param scoreStorage the score storage
	 */
	@Autowired
	public void setScoreStorage(final ScoreStorage scoreStorage) {
		this.scoreStorage = scoreStorage;
	}
}
