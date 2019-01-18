
import java.util.Random;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;

/*******************************************************************************
 * TriviaGame contains the trivia game for the application. It pulls random 
 * popular movies from TMDb and asks questions on it for the user to answer. 
 * The game keeps track of correct and incorrect
 * answers.
 * 
 * @author Kimberlin Steffens, Raymundo Ramirez, Camaal Murray, Nicolas Heady
 * @version First Release
 ******************************************************************************/
public class TriviaGame {

  /** Counts the number of questions correct. */
  private int numCorrect;

  /** Counts the number of questions incorrect. */
  private int numIncorrect;

  /** Tracks the current question number. */
  private int questionNum;

  /** The text to be displayed to the player. */
  private String leftText;
  
  /** The text to be displayed to the player. */
  private String rightText;

  /** The movie being quizzed on. */
  private MovieDb quizzedMovie;

  /** The question is being asked. */
  private int questionType;
  
  /** Used to display the correct answer after an incorrect guess. */
  private String correctAnswer;

  /** The api key. */
  private TmdbApi tmdbApi;

  /** The database of movies. */
  private TmdbMovies movies;

  /** Whether the game is over or not. */
  private boolean gameOver;
  
  /** Number of questions to win. */
  private final int toWinNum = 7;
  
  /** Number of questions to lose. */
  private final int toLoseNum = 3;

  /*****************************************************************************
   * The constructor for TrviviaGame. Instantiates all instance variables and 
   * sets the text to a welcome message.
   ****************************************************************************/
  public TriviaGame() {

    // instantiate everything
    numCorrect = 0;
    numIncorrect = 0;
    questionNum = 1;
    gameOver = false;
    leftText = "Welcome to the Trivia Game! Get 7 out of 10 questions correct and you win!";
    rightText = "This is where corrections will be displayed.";
    correctAnswer = "";

    tmdbApi = new TmdbApi("d2daaf6a236119d2b3020ba876471949");

    movies = tmdbApi.getMovies();

    // begin the quiz
    quiz();
  }

  /*****************************************************************************
   * Method to quiz the user. A random type of question is chosen and the 
   * question number is incremented.
   ****************************************************************************/
  public final void quiz() {
    Random generator = new Random();

    // random question type. currently 5 types
    questionType = generator.nextInt(5);

    leftText += "\n Question number " + questionNum;

    // ask the random question
    if (questionType == 0) {
      quizGenre();

    } else if (questionType == 1) {
      quizActor();

    } else if (questionType == 2) {
      quizDate();

    } else if (questionType == 3) {
      quizTagline();

    } else if (questionType == 4) {
      quizDirector();
    }
    
  }

  /*****************************************************************************
   * Quizzes on the genre of a movie. First picks a random popular movie to quiz
   * on and then asks the user.
   ****************************************************************************/
  public final void quizGenre() {
    quizzedMovie = randomPopMovie();
    leftText += "\n What genre is the movie " + quizzedMovie.getTitle() + "?";
  }

  /*****************************************************************************
   * Quizzes on the cast of a movie. First picks a random popular movie to quiz
   *  on and then asks the user.
   ****************************************************************************/
  public final void quizActor() {
    quizzedMovie = randomPopMovie();
    leftText += "\n Who is an actor that starred in " + quizzedMovie.getTitle() 
    + "?";
  }

  /*****************************************************************************
   * Quizzes on the year released of a movie. First picks a random popular movie
   * to quiz on and then asks the user.
   ****************************************************************************/
  public final void quizDate() {
    quizzedMovie = randomPopMovie();
    leftText += "\n What year was " + quizzedMovie.getTitle() + " released?";
  }

  /*****************************************************************************
   * Quizzes on the tagline of a movie. First picks a random popular movie to
   *  quiz on and then asks the user.
   ****************************************************************************/
  public final void quizTagline() {
    quizzedMovie = randomPopMovie();

    // make sure there is a tagline for the movie
    while (quizzedMovie.getTagline().equals("")) {
      quizzedMovie = randomPopMovie();
    }

    leftText += "\n What movie has the tagline \"" + quizzedMovie.getTagline()
+ "\"?";
  }

  /*****************************************************************************
   * Quizzes on the director of a movie. First picks a random popular movie to 
   * quiz on and then asks the user.
   ****************************************************************************/
  public final void quizDirector() {
    quizzedMovie = randomPopMovie();
    leftText += "\n Who is the director of " + quizzedMovie.getTitle() + "?";
  }

  /*****************************************************************************
   * Method for the user to answer the question.
   * 
   * @param answer
   *          The user's given answer.
   ****************************************************************************/
  public final void answer(final String answer) {

    // if they were correct or not
    boolean isCorrect = false;
    
    correctAnswer = "";

    // genre question
    if (questionType == 0) {

      // loop through all genres to see if answer is there
      for (Genre genre : quizzedMovie.getGenres()) {
        correctAnswer += "\n" + genre.getName() + "\n";
        if (genre.getName().equalsIgnoreCase(answer)) {
          isCorrect = true;
        }
      }

      // actor question
    } else if (questionType == 1) {

      // loop through all cast members to see if answer is there
      for (PersonCast actor : quizzedMovie.getCast()) {
        
        correctAnswer += "\n" + actor.getName() + "\n";
       
        if (actor.getName().equalsIgnoreCase(answer)) {
          isCorrect = true;
        }
      }

      // release date question
    } else if (questionType == 2) {
      correctAnswer += "\n" + quizzedMovie.getReleaseDate().substring(0, 4);
      if (quizzedMovie.getReleaseDate().substring(0, 4)
          .equalsIgnoreCase(answer)) {
        isCorrect = true;
      }

      // tagline question
    } else if (questionType == 3) {
      correctAnswer += "\n" + quizzedMovie.getTitle();
      if (quizzedMovie.getTitle().equalsIgnoreCase(answer)) {
        isCorrect = true;

      }
    } else if (questionType == 4) {
      for (PersonCrew crew : quizzedMovie.getCrew()) {
        if (crew.getJob().equalsIgnoreCase("director")) {
          correctAnswer += "\n" + crew.getName() + "\n";
          if (crew.getName().equalsIgnoreCase(answer)) {
            isCorrect = true;
          }
        }
      }
    }

    // update text and include the current score
    if (isCorrect) {
      numCorrect++;
      leftText += "\n Correct! Current Score: correct - " + numCorrect 
          + " incorrect - " + numIncorrect;
    } else {
      numIncorrect++;
      leftText += "\n Sorry that was incorrect. \n Current Score: correct - " 
      + numCorrect
          + " incorrect - " + numIncorrect;
      rightText += "\n \n The correct answer to question " + questionNum
          + " is: " + correctAnswer;
    }

    // if game is over
    if (numIncorrect == toLoseNum) {
      gameOver = true;

    } else if (numCorrect == toWinNum) {
      gameOver = true;

      // next question
    } else {
      leftText += " \n Next Question. \n";
      
   // question number goes up
      questionNum++;
      
      // continue quizzing
      quiz();
    }
  }

  /*****************************************************************************
   * Finds a random popular movie to quiz on.
   * 
   * @return A popular MovieDb to be quizzed on.
   ****************************************************************************/
  public final MovieDb randomPopMovie() {

    MovieResultsPage results = movies.getPopularMovies("en", 0);
    List<MovieDb> popularMovies = results.getResults();
    int numMovies = popularMovies.size();

    int randomNum = (int) (Math.random() * (numMovies - 0) + 0);
    int movieId = popularMovies.get(randomNum).getId();

    MovieDb randomMovie = this.movies.getMovie(movieId, "en", 
        TmdbMovies.MovieMethod.credits);

    return randomMovie;
  }

  /*****************************************************************************
   * Gets a poster for the quizzed movie.
   * 
   * @return A string with the URL of the poster.
   ****************************************************************************/
  public final String getPoster() {
    String url = "https://image.tmdb.org/t/p/original";
    String path = quizzedMovie.getPosterPath();
    url += path;
    return url;
  }

  /*****************************************************************************
   * Getter method for numCorrect.
   * 
   * @return the numCorrect
   ****************************************************************************/
  public final int getNumCorrect() {
    return numCorrect;
  }

  /*****************************************************************************
   * Setter method for numCorrect.
   * 
   * @param newCorrect
   *          the numCorrect to set
   ****************************************************************************/
  public final void setNumCorrect(final int newCorrect) {
    this.numCorrect = newCorrect;
  }

  /*****************************************************************************
   * Getter method for numIncorrect.
   * 
   * @return the numIncorrect
   ****************************************************************************/
  public final int getNumIncorrect() {
    return numIncorrect;
  }

  /*****************************************************************************
   * Setter method for numIncorrect.
   * 
   * @param newIncorrect
   *          the numIncorrect to set
   ****************************************************************************/
  public final void setNumIncorrect(final int newIncorrect) {
    this.numIncorrect = newIncorrect;
  }

  /*****************************************************************************
   * Getter method for questionNum.
   * 
   * @return the questionNum
   ****************************************************************************/
  public final int getQuestionNum() {
    return questionNum;
  }

  /*****************************************************************************
   * Setter method for questionNum.
   * 
   * @param newQuestionNum
   *          the questionNum to set
   ****************************************************************************/
  public final void setQuestionNum(final int newQuestionNum) {
    this.questionNum = newQuestionNum;
  }

  /*****************************************************************************
   * Getter method for text.
   * 
   * @return the text
   ****************************************************************************/
  public final String getLeftText() {
    return leftText;
  }

  /*****************************************************************************
   * Setter method for the text.
   * 
   * @param newText
   *          the text to set
   ****************************************************************************/
  public final void setText(final String newText) {
    this.leftText = newText;
  }

  /*****************************************************************************
   * Getter method for gameOver.
   * 
   * @return the gameOver
   ****************************************************************************/
  public final boolean isGameOver() {
    return gameOver;
  }

  /*****************************************************************************
   * Setter method for gameOver.
   * 
   * @param isOver
   *          the gameOver to set
   ****************************************************************************/
  public final void setGameOver(final boolean isOver) {
    this.gameOver = isOver;
  }

  /*****************************************************************************
   * @return the rightText
   ****************************************************************************/
  public final String getRightText() {
    return rightText;
  }

  /*****************************************************************************
   * @param rText the rightText to set
   ****************************************************************************/
  public final void setRightText(final String rText) {
    this.rightText = rText;
  }

}