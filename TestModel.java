import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.SessionToken;

public class TestModel {
	
	/** The TmdbApi object. */
	  private TmdbApi tmdbApi;
	  
	  //tmdbApi = new TmdbApi("dd2378082d2303539238c2a2ab898957");

	  /** The SessionToken for specific methods. */
	  private SessionToken sessionToken;

  	/***
  	 * Test the constructor and the API value.
  	 */
	@Test
	public void testModel() {
		Model m = new Model();
		TmdbApi test = new TmdbApi("dd2378082d2303539238c2a2ab898957");
		//Ensure that the right key is passed and not null
		assertEqual(test, m.getApi());
		
	}
	/***
	 * Test get first Movie poster with known return
	 */
	@Test
	public void testgetPoster(){
		Model m = new Model();
		BufferedImage b =  m.getFirstMoviePoster("war");
		
		assertNotNull(b);
	}
	
	/***
	 * Testing the scaled image
	 * 
	 */
	@Test
	public void testGetScaledImage() {
		Model m = new Model();
		BufferedImage b =  m.getFirstMoviePoster("war");
		BufferedImage a =  m.getScaledImage(b, b.getWidth(), b.getHeight());
		
		 int finalw;
		 int finalh;
		 
		 finalw = b.getWidth();
		 finalh = b.getHeight();
		 
		 double factor = 1.0d;
		    if (b.getWidth() > b.getHeight()) {
		      factor = ((double) b.getHeight() / (double) b.getWidth());
		      finalh = (int) (finalw * factor);
		    } else {
		      factor = ((double) b.getWidth() / (double) b.getHeight());
		      finalw = (int) (finalh * factor);
		    }

		
		//check to see if image is resized != to the original
	    assertFalse((b.getWidth() == finalw));
		
	}
	

	private void assertFalse(Object assertEquals) {
		// TODO Auto-generated method stub
		
	}
	private void assertEqual(TmdbApi test, TmdbApi api) {
		// TODO Auto-generated method stub
		
	}

}
