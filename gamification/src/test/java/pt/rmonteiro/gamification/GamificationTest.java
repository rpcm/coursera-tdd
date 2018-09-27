package pt.rmonteiro.gamification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.RuntimeConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import pt.rmonteiro.gamification.mock.MockScoreStorage;
import pt.rmonteiro.gamification.repository.ScoreRepository;

/**
 * Week 4 exercise of TDD course. 
 * 
 * https://www.coursera.org/learn/tdd-desenvolvimento-de-software-guiado-por-testes/home/week/4
 * 
 * Unit test for {@link ScoreBoard} and {@link MockScoreStorage}.
 * 
 * @author ruimonteiro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-config.xml")
@Component
public class GamificationTest {
	
	private static final String LOCALHOST = "127.0.0.1";
    private static final String DB_NAME = "gamificationTest";
    private static final int MONGO_TEST_PORT = 27017;
	
	private static final String USER_GUERRA = "guerra";
	private static final String USER_FERNANDES = "fernandes";
	private static final String USER_RODRIGO = "rodrigo";
	private static final String USER_TOCO = "toco";
	private static final String USER_RMONTEIRO = "rmonteiro";
	
	private static MongodProcess mongoProcess;
    private static MongoClient mongoClient;
	
    @Autowired
	private ScoreStorage scoreStorage;
	
	private MockScoreStorage mockScoreStorage;
	private ScoreBoard scoreBoard;
	
	@BeforeClass
    public static void initializeDB() throws IOException {

        RuntimeConfig config = new RuntimeConfig();
        config.setExecutableNaming(new UserTempNaming());

        MongodStarter starter = MongodStarter.getInstance(config);

        MongodExecutable mongoExecutable = starter.prepare(new MongodConfig(Version.V2_2_0, MONGO_TEST_PORT, false));
        mongoProcess = mongoExecutable.start();

        mongoClient = new MongoClient(LOCALHOST, MONGO_TEST_PORT);
        mongoClient.getDB(DB_NAME);
    }

    @AfterClass
    public static void shutdownDB() throws InterruptedException {
    	mongoClient.close();
        mongoProcess.stop();
    }
	
	@Before
	public void init() {
		scoreBoard = new ScoreBoard();
		mockScoreStorage = new MockScoreStorage();
		scoreBoard.setScoreStorage(mockScoreStorage);
		setupDataMockScoreStorage();
	}
	
	@After
    public void tearDown() throws Exception {
		((ScoreRepository) scoreStorage).getMongoOperations().dropCollection(Score.class);
    }
	
	/**	
     * Test save score
     */
	@Test
    public void saveScore() {
		final int total = 10;
		final ScoreType type = ScoreType.STAR;
		final Score score = scoreStorage.saveScore(USER_GUERRA, total, type);
		assertEquals(USER_GUERRA, score.getUser());
		assertEquals(total, score.getTotal());
		assertEquals(type, score.getType());
    }
	
	/**	
     * Test get total score of a given user and score type
     */
	@Test
    public void getTotalScoreOfUserByScoreType() {
		setupDataScoreStorage();
		final int totalScore = scoreStorage.getTotalScore(USER_GUERRA, ScoreType.STAR);
		assertEquals(25, totalScore);
    }
	
	/**	
     * Test get users with scores
     */
	@Test
    public void getUsersWithScores() {
		setupDataScoreStorage();
		final Set<String> users = scoreStorage.getUsersWithScores();
		assertEquals(4, users.size());
		assertTrue(users.contains(USER_GUERRA));
		assertTrue(users.contains(USER_FERNANDES));
		assertTrue(users.contains(USER_RODRIGO));
		assertTrue(users.contains(USER_TOCO));
    }
	
	/**	
     * Test get score types with score
     */
	@Test
    public void getScoreTypesWithScore() {
		setupDataScoreStorage();
		final Set<ScoreType> scoreTypes = scoreStorage.getScoreTypesWithScore();
		assertEquals(3, scoreTypes.size());
		assertTrue(scoreTypes.contains(ScoreType.STAR));
		assertTrue(scoreTypes.contains(ScoreType.COIN));
		assertTrue(scoreTypes.contains(ScoreType.ENERGY));
	}
	
	/**	
     * Test add score
     */
	@Test
    public void addScore() {
		final int total = 10;
		final ScoreType type = ScoreType.STAR;
		final Score score = scoreBoard.addScore(USER_GUERRA, total, type);
		assertEquals(USER_GUERRA, score.getUser());
		assertEquals(total, score.getTotal());
		assertEquals(type, score.getType());
		mockScoreStorage.verifyScore(score);
    }
	
	/**	
     * Test get user scores
     */
	@Test
    public void getUserScores() {
		final List<Score> scores = scoreBoard.getScores(USER_GUERRA);
		assertEquals(2, scores.size());
		assertTrue(scores.contains(new Score(USER_GUERRA, 25, ScoreType.STAR)));
		assertTrue(scores.contains(new Score(USER_GUERRA, 20, ScoreType.COIN)));
    }
	
	/**	
     * Test score ranking by score type
     */
	@Test
    public void getScoreRankingByScoreType() {
		final List<Score> ranking = scoreBoard.getRanking(ScoreType.STAR);
		assertEquals(3, ranking.size());
		assertEquals(USER_GUERRA, ranking.get(0).getUser());
		assertEquals(25, ranking.get(0).getTotal());
		assertEquals(ScoreType.STAR, ranking.get(0).getType());
		assertEquals(USER_FERNANDES, ranking.get(1).getUser());
		assertEquals(19, ranking.get(1).getTotal());
		assertEquals(ScoreType.STAR, ranking.get(1).getType());
		assertEquals(USER_RODRIGO, ranking.get(2).getUser());
		assertEquals(17, ranking.get(2).getTotal());
		assertEquals(ScoreType.STAR, ranking.get(2).getType());
    }
	
	/**	
     * Test integration with score board and score storage
     */
	@Test
    public void integrationScoreBoardWithScoreStorage() {
		final int total = 100;
		final ScoreType type = ScoreType.LIKE;
		final Score score = scoreBoard.addScore(USER_RMONTEIRO, total, type);
		assertEquals(USER_RMONTEIRO, score.getUser());
		assertEquals(total, score.getTotal());
		assertEquals(type, score.getType());
		mockScoreStorage.verifyScore(score);
		
		final List<Score> ranking = scoreBoard.getRanking(ScoreType.LIKE);
		assertEquals(1, ranking.size());
		assertEquals(USER_RMONTEIRO, ranking.get(0).getUser());
		assertEquals(100, ranking.get(0).getTotal());
		assertEquals(ScoreType.LIKE, ranking.get(0).getType());
		
		final List<Score> userScores = scoreBoard.getScores(USER_RMONTEIRO);
		assertEquals(1, ranking.size());
		assertEquals(USER_RMONTEIRO, userScores.get(0).getUser());
		assertEquals(100, userScores.get(0).getTotal());
		assertEquals(ScoreType.LIKE, userScores.get(0).getType());
    }
	
	/**
	 * Setup of the data in the mock score storage
	 */
	private void setupDataMockScoreStorage() {
		mockScoreStorage.saveScore(USER_GUERRA, 25, ScoreType.STAR);
		mockScoreStorage.saveScore(USER_GUERRA, 20, ScoreType.COIN);
		mockScoreStorage.saveScore(USER_FERNANDES, 19, ScoreType.STAR);
		mockScoreStorage.saveScore(USER_RODRIGO, 17, ScoreType.STAR);
		mockScoreStorage.saveScore(USER_TOCO, 50, ScoreType.ENERGY);
	}
	
	/**
	 * Setup of the data in the score storage
	 */
	private void setupDataScoreStorage() {
		scoreStorage.saveScore(USER_GUERRA, 25, ScoreType.STAR);
		scoreStorage.saveScore(USER_GUERRA, 20, ScoreType.COIN);
		scoreStorage.saveScore(USER_FERNANDES, 19, ScoreType.STAR);
		scoreStorage.saveScore(USER_RODRIGO, 17, ScoreType.STAR);
		scoreStorage.saveScore(USER_TOCO, 50, ScoreType.ENERGY);
	}
}
