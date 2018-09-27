package pt.rmonteiro.gamification.mock;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pt.rmonteiro.gamification.ScoreStorage;
import pt.rmonteiro.gamification.Score;
import pt.rmonteiro.gamification.ScoreType;

/**
 * Week 4 exercise of TDD course. 
 * 
 * https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/4
 * 
 * The mock of the score storage
 * 
 * @author ruimonteiro
 */
public class MockScoreStorage implements ScoreStorage {
	
	private Map<String, List<Score>> scores = new HashMap<>();

	/**
	 * @see pt.rmonteiro.gamification.ScoreStorage#saveScore(java.lang.String, int, pt.rmonteiro.gamification.ScoreType)
	 */
	@Override
	public Score saveScore(final String user, int total, final ScoreType type) {
		List<Score> userScores = new ArrayList<>();
		if(scores.containsKey(user)) {
			userScores = scores.get(user);
		}
		final Score score = new Score(user, total, type);
		userScores.add(score);
		scores.put(user, userScores);
		return score;
	}

	/**
	 * @see pt.rmonteiro.gamification.ScoreStorage#getTotalScore(java.lang.String, pt.rmonteiro.gamification.ScoreType)
	 */
	@Override
	public int getTotalScore(final String user, final ScoreType type) {
		final List<Score> scoresByUser = scores.get(user);
		int totalScore = 0;
		for (Score score : scoresByUser) {
			if(score.getType() == type) {
				return totalScore += score.getTotal();
			}
		}
		return totalScore;
	}

	/**
	 * @see pt.rmonteiro.gamification.ScoreStorage#getUsersWithScores()
	 */
	@Override
	public Set<String> getUsersWithScores() {
		return scores.keySet();
	}
	
	/**
	 * @see pt.rmonteiro.gamification.ScoreStorage#getScoreTypesWithScore()
	 */
	@Override
	public Set<ScoreType> getScoreTypesWithScore() {
		final Set<ScoreType> scoreTypes = new HashSet<>();
		for (List<Score> allScores : scores.values()) {
			for (Score score : allScores) {
				scoreTypes.add(score.getType());
			}
		}
		return scoreTypes;
	}
	
	/**
	 * Checks if the given score exists
	 * @param score the score to be verified
	 */
	public void verifyScore(final Score score) {
		final List<Score> userScores = scores.get(score.getUser());
		assertTrue(userScores.contains(score));
	}
}
