package imdb.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import imdb.entity.Movie;
import imdb.service.MovieServiceImplementation;

@Configuration

@EnableScheduling
public class MovieJsonParser {

	@Autowired
	private MovieServiceImplementation movieService;

	@Scheduled(fixedRate = 5000)
	public void readFromURL() throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();

		try {
			URL oracle = new URL("http://localhost:3002/movie");
			URLConnection con = oracle.openConnection();
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

			JSONArray jsnArray = (JSONArray) jsonParser.parse(buffReader);

			for (Object movieObj : jsnArray) {
				addInDB(movieObj);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void addInDB(Object movieObj) {

		// Get movie object within list
		JSONObject jSONMovieObject = (JSONObject) movieObj;
		String title = (String) jSONMovieObject.get("Title");

		if (!movieService.isAlreadyInDB(title)) {

			Movie movie = new Movie();
			movie.setTitle(title); // System.out.println(title);

			String release = (String) jSONMovieObject.get("Release");
			movie.setRelease(release);

			String genre = (String) jSONMovieObject.get("Genre");
			movie.setGenre(genre);

			String time = (String) jSONMovieObject.get("Time");
			movie.setTime(time);

			String director = (String) jSONMovieObject.get("Director");
			movie.setDirector(director);

			String stars = (String) jSONMovieObject.get("Stars");
			movie.setStars(stars);

			movieService.saveMovie(movie);
			System.out.println("Movie added succefully!");
		} else {
			System.out.println("The movie:" + title + "already exists.");
		}
	}
}
