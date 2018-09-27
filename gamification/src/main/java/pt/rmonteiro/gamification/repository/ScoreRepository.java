package pt.rmonteiro.gamification.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import pt.rmonteiro.gamification.ScoreStorage;
import pt.rmonteiro.gamification.Score;
import pt.rmonteiro.gamification.ScoreType;

/**
 * Week 4 exercise of TDD course. 
 * 
 * https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/4
 * 
 * The score repository
 * 
 * @author ruimonteiro
 */
@Repository
public class ScoreRepository implements ScoreStorage {

	@Autowired
	private MongoOperations mongoOperations;
	
	/**
	 * @see pt.rmonteiro.gamification.ScoreStorage#saveScore(java.lang.String, int, pt.rmonteiro.gamification.ScoreType)
	 */
	@Override
	public Score saveScore(String user, int total, ScoreType type) {
		final Score score = new Score(user, total, type);
		mongoOperations.save(score);
		return score;
	}

	/**
	 * @see pt.rmonteiro.gamification.ScoreStorage#getTotalScore(java.lang.String, pt.rmonteiro.gamification.ScoreType)
	 */
	@Override
	public int getTotalScore(String user, ScoreType type) {
		final List<Score> userScores = mongoOperations.find(query(where("user").is(user).and("type").is(type)), Score.class);
		int totalScore = 0;
		for (Score score : userScores) {
			totalScore += score.getTotal();
		}
		return totalScore;
	}

	/**
	 * @see pt.rmonteiro.gamification.ScoreStorage#getUsersWithScores()
	 */
	@Override
	public Set<String> getUsersWithScores() {
		final Set<String> users = new HashSet<>();
		final List<Score> findAll = mongoOperations.findAll(Score.class);
		for (Score score : findAll) {
			users.add(score.getUser());
		}
		return users;
	}

	/**
	 * @see pt.rmonteiro.gamification.ScoreStorage#getScoreTypesWithScore()
	 */
	@Override
	public Set<ScoreType> getScoreTypesWithScore() {
		final Set<ScoreType> types = new HashSet<>();
		final List<Score> findAll = mongoOperations.findAll(Score.class);
		for (Score score : findAll) {
			types.add(score.getType());
		}
		return types;
	}

	/**
	 * @return the mongo operations
	 */
	public MongoOperations getMongoOperations() {
		return mongoOperations;
	}
}
