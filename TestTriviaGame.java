import static org.junit.Assert.*;

import org.junit.Test;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;




public class TestTriviaGame {
	
	 /** Counts the number of questions correct. */
	  private int numCorrect;

	  /** Counts the number of questions incorrect. */
	  private int numIncorrect;

	  /** Tracks the current question number. */
	  private int questionNum;

	  /** The text to be displayed to the player. */
	  private String text;

	  /** The movie being quizzed on. */
	  private MovieDb quizzedMovie;

	  /** The question is being asked. */
	  private int questionType;

	  /** The api key. */
	  private TmdbApi tmdbApi;

	  /** The database of movies. */
	  private TmdbMovies movies;
	  
	  /** Whether the game is over or not. */
	  private boolean gameOver;

	/***
	 * Test the get poster function with known input
	 */
	@Test
	public void testGetPoster() {
		TriviaGame g = new TriviaGame();
		assertNotNull(g.getPoster());		
		
	}
	/***
	 * Tests set and get numCorrect
	 */
	@Test
	public void testSetNumCorrect() {
		TriviaGame g = new TriviaGame();
		g.setNumCorrect(100);
			
		assertEquals(100, g.getNumCorrect());
		
	}
	/***
	 * Tests set and get numIncorrect
	 */
	@Test
	public void testSetNumInCorrect() {
		TriviaGame g = new TriviaGame();
		g.setNumIncorrect(7);
			
		assertEquals(7, g.getNumIncorrect());
		
	}
	/***
	 * Test set and get QuestionNum
	 */
	@Test
	public void testGetQuestionNum() {
		TriviaGame g = new TriviaGame();
		g.setQuestionNum(7);
			
		assertEquals(7, g.getQuestionNum());
		
	}
	/***
	 * Tests set and get text
	 */
	@Test
	public void testSetText() {
		TriviaGame g = new TriviaGame();
		g.setText("Hello");
		String s = "Hello";
			
		assertEquals(s, g.getText());
		
	}
	/***
	 * Tests set and get Game status
	 */
	@Test
	public void testSetGameOver() {
		TriviaGame g = new TriviaGame();
		g.setGameOver(true);
			
		assertEquals(true, g.isGameOver());
		
	}
	

	

}
