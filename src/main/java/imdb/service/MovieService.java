package imdb.service;

import imdb.entity.Movie;

public interface MovieService {
	
	public void saveMovie(Movie movie);
	
	public boolean isAlreadyInDB(String name);
	
}
