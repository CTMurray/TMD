import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

import java.util.*;



public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//API Key
		TmdbApi tmdbApi = new TmdbApi("dd2378082d2303539238c2a2ab898957");
		
		TmdbMovies movies = tmdbApi.getMovies();
		TmdbKeywords key = tmdbApi.getKeywords();
		
		MovieImages m = new MovieImages();
		
		m.getPosters();

		
		TmdbSearch search = new TmdbSearch(tmdbApi);
		
		TmdbSearch firstSearch = new TmdbSearch(tmdbApi);
		
		
		
		//MovieDb movie = movies.getMovie(550,  "en");//550 5353
		


		MovieResultsPage resultsPage = firstSearch.searchMovie("Hunting", 2007, "en", false, 0);


		List<MovieDb> results = resultsPage.getResults();


		for (MovieDb mov : results) {

		System.out.println(mov.getTitle());

		}
		
		/*
		int i = 0;
		Map mov = new TreeMap();
		boolean count = false;
		
		String movT;
		MovieDb m;
		while(!count ){
			
			if((m = movies.getMovie(i, "en")) != null){
				movT = movies.getMovie(i, "en").toString();
				mov.put(i, movT);
			}
			
			if(i == 6000) {
				break;
			}
			
			
			i++;
		}
		*/
		
		//TmdbKeywords keywords = tmdbApi.getKeywords(); 
				
		//get information on  a movie

		//MovieDb movie = movies.getMovie(5353,  "en");
		MovieDb movie = movies.getMovie(550,  "en");//550 5353
		
				
		//System.out.println("poster: " ));
		
		//movies.getVideos(550, "en");
		
		System.out.println("Videos: " + movies.getVideos(550, "en"));
		
		System.out.println("Keywords: " + movies.getKeywords(550));
		
		System.out.println("Title: " + movie.getTitle());
		System.out.println("Tag Line: " + movie.getTagline());
		System.out.println("Release date: " + movie.getReleaseDate());
		System.out.println("Run time: " + movie.getRuntime());
		System.out.println("Homepage: " + movie.getHomepage());
		System.out.println("Production Companies: " + movie.getProductionCompanies());
		System.out.println("Overview: " + movie.getOverview());
		System.out.println("Popularity: " + movie.getPopularity());
		System.out.println("Homepage: " + movie.getHomepage());
		System.out.println("this is an adult movie right? " + movie.isAdult());
		System.out.println("Budget: " + (int)movie.getBudget());
		System.out.println("Genre: " + movie.getGenres());
		System.out.println("Poster Path: " + movie.getPosterPath());
		System.out.println("Production Countries: " + movie.getProductionCountries());
		System.out.println("Status: " + movie.getStatus());
		System.out.println("Average vote: " + movie.getVoteAverage());
		System.out.println("Spoken Languages: " + movie.getSpokenLanguages());
		System.out.println("Revenue: " + movie.getRevenue());

	}

}

/*
 * 
 * {"adult":false,"backdrop_path":"/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg","belongs_to_collection":null,"budget":63000000,"genres":[{"id":18,"name":"Drama"}],
 * "homepage":"http://www.foxmovies.com/movies/fight-club","id":550,"imdb_id":"tt0137523","original_language":"en",
 * "original_title":"Fight Club",
 * "overview":"A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. 
 * Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral
 *  toward oblivion.","popularity":5.914887,"poster_path":"/811DjJTon9gD6hZ8nCjSitaIXFQ.jpg","production_companies":[{"name":"Regency Enterprises","id":508},
 *  {"name":"Fox 2000 Pictures","id":711},{"name":"Taurus Film","id":20555},{"name":"Linson Films","id":54050},{"name":"Atman Entertainment","id":54051},
 *  {"name":"Knickerbocker Films","id":54052}],"production_countries":[{"iso_3166_1":"DE","name":"Germany"},{"iso_3166_1":"US",
 *  "name":"United States of America"}],"release_date":"1999-10-14","revenue":100853753,"runtime":139,
 *  "spoken_languages":[{"iso_639_1":"en","name":"English"}],"status":"Released",
 *  "tagline":"How much can you know about yourself if you've never been in a fight?",
 *  "title":"Fight Club","video":false,"vote_average":8.0,"vote_count":4762}
*/