
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbMovies.MovieMethod;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.PersonCrew;

/*******************************************************************************
 * Class that houses all methods accessing the Api.
 * 
 * @author Kimberlin Steffens, Raymundo Ramirez, Camaal Murray, Nicolas Heady
 * @version First Release
 ******************************************************************************/
public class Model {

  /** The TmdbApi object. */
  private TmdbApi tmdbApi;
  
  /** The TmdbMovies object. */
  private TmdbMovies tmdbMovies;
  
  /** The results from a search. */
  private List<MovieDb> results;
  
  /** The limit of cast to display. */
  private final int castLimit = 4;

  /*****************************************************************************
   * The constructor.
   ****************************************************************************/
  public Model() {
    tmdbApi = new TmdbApi("dd2378082d2303539238c2a2ab898957");
    tmdbMovies = tmdbApi.getMovies();
  }

  /*****************************************************************************
   * Gets the current TmdbApi.
   * 
   * @return the TmdbApi
   ****************************************************************************/
  public final TmdbApi getApi() {
    return this.tmdbApi;
  }

  /*****************************************************************************
   * Performs a search for movies with the keyword in the title only.
   * 
   * @param keyword
   *          the searched keyword(s).
   * @return a list of movie titles.
   ****************************************************************************/
  public final ArrayList<String> searchForTitles(final String keyword) {

    ArrayList<String> movieTitles = new ArrayList<String>();

    TmdbSearch titleSearch = new TmdbSearch(tmdbApi);
    MovieResultsPage resultsPage = 
        titleSearch.searchMovie(keyword, 0, "en", false, 0);

    // Get a list of MovieDb from the MovieResultsPage.
    this.results = resultsPage.getResults();

    for (MovieDb movie : results) {
      movieTitles.add(movie.getTitle());
    }

    return movieTitles;
  }

  /*****************************************************************************
   * Gets the movie poster from the first movie in the results.
   * 
   * @param keyword
   *          the search keyword
   * @return the movie poster as a BufferedImage
   ****************************************************************************/
  public final BufferedImage getFirstMoviePoster(final String keyword) {

    TmdbSearch movieSearch = new TmdbSearch(tmdbApi);
    MovieResultsPage resultsPage = 
        movieSearch.searchMovie(keyword, 0, "en", false, 0);

    // Get a list of MovieDb from the MovieResultsPage.
    this.results = resultsPage.getResults();

    URL url = null;

    try {
      url = new URL("https://image.tmdb.org/t/p/original" 
    + results.get(0).getPosterPath());
    } catch (MalformedURLException e1) {
      System.out.println(e1.getMessage());
      
      // Show "No Poster" text
      e1.printStackTrace();
    }

    BufferedImage image = null;

    try {
      image = ImageIO.read(url);
    } catch (IOException e1) {
      System.out.println(e1.getMessage());
      e1.printStackTrace();
    }

    if (image != null) {
      return image;
    } else {
      
      // Do something?
      return null;
    }
  }
  
  /*****************************************************************************
   * Returns the poster of a movie.
   * 
   * @param posterSrc
   * 					the URL of the movie poster
   * @return the BufferedImage of the poster
   ****************************************************************************/
  public final BufferedImage getMoviePoster(final String posterSrc) {

	  URL url = null;
	  BufferedImage image = null;
	  
	  try {
	      url = new URL("https://image.tmdb.org/t/p/original" + posterSrc);
	    } catch (MalformedURLException ex) {
	    	JOptionPane.showMessageDialog(null,
					"Sorry, but an error "
					+ "occurred while trying to load the poster URL:\n"
					+ ex);
	    }
	  
	  try {
	      image = ImageIO.read(url);
	    } catch (IOException ex) {
	    	JOptionPane.showMessageDialog(null,
					"Sorry, but an error "
					+ "occurred while trying to load the poster image:\n" 
					+ ex);
	    }
	  
	  return image;
  }
  
  /*****************************************************************************
   * Gets a new poster when a new title is clicked on.
   * @param title the title of the movie.
   * @return the new BufferedImage
   ****************************************************************************/
  public final BufferedImage getNewPoster(final String title) {
	  int index = this.getIndexOfMovie(title);
	  String posterPath = this.results.get(index).getPosterPath();
	  return this.getMoviePoster(posterPath);
	  
  }
  
  /*****************************************************************************
   * Generates the text to go in the details display area.
   * @param title the title of the movie.
   * @return the String to be displayed
   ****************************************************************************/
  public final String generateDetails(final String title) {
	  String details = "";
	  int index = this.getIndexOfMovie(title.trim());
	  int movieId = this.results.get(index).getId();
	  MovieDb movie = this.tmdbMovies.getMovie(movieId, "en",
			  MovieMethod.credits);
	  details = "Title: " + movie.getTitle() + "\n";
	  details += "Runtime: " + movie.getRuntime() + " minutes\n";
	  
	  for (PersonCrew crew : movie.getCrew()) {
		  if (crew.getJob().equalsIgnoreCase("Director")) {
			  details += "Director: " + crew.getName() + "\n";
			  break;
		  }
	  }
	  
	  details += "\nCast:\n";
	  for (int i = 0; i < movie.getCast().size(); i++) {
		  details += movie.getCast().get(i).getName() + "\n";
		  if (i == castLimit) {
			  break;
		  }
	  }
	  
	//Added Release date and taglines to help with the trivia game  
	  details += "\nRelease Date:\n";
	  details += movie.getReleaseDate() + "\n";
	  
	  details += "\nTagline:\n";
	  details += movie.getTagline() + "\n";
	  
	  
	  
	  return details;
  }
  
  /*****************************************************************************
   * Takes the given movie title and searches the array "results".
   * Gets its index.
   * 
   * @param title the title of the movie.
   * @return the index of the movie in the array
   ****************************************************************************/
  private int getIndexOfMovie(final String title) {
	  int index = 0;
	  for (int i = 0; i < this.results.size(); i++) {
		  if (this.results.get(i).getTitle().equals(title.trim())) {
			  index = i;
			  break;
		  }
	  }
	  return index;
  }

  /*****************************************************************************
   * Resizes image to desired size.
   * 
   * @param src
   *          the image to resize
   * @param w
   *          new width
   * @param h
   *          new height
   * @return the resized BufferedImage
   ****************************************************************************/
  public final BufferedImage getScaledImage(final BufferedImage src, 
      final int w, final int h) {

    int finalw = w;
    int finalh = h;
    double factor = 1.0d;
    if (src.getWidth() > src.getHeight()) {
      factor = ((double) src.getHeight() / (double) src.getWidth());
      finalh = (int) (finalw * factor);
    } else {
      factor = ((double) src.getWidth() / (double) src.getHeight());
      finalw = (int) (finalh * factor);
    }

    BufferedImage resizedImg = 
        new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);

    Graphics2D g2 = resizedImg.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);

    g2.drawImage(src, 0, 0, finalw, finalh, null);
    g2.dispose();
    return resizedImg;
  }
}
