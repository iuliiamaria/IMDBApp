package imdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imdb.entity.Movie;
import imdb.repository.MovieRepository;

@Service("movieService")
public class MovieServiceImplementation implements MovieService{

	@Autowired
	MovieRepository movieRepository;
	
	@Override
	public void  saveMovie(Movie movie) {
	
		movieRepository.save(movie);
	}

	@Override
	public boolean isAlreadyInDB(String name) {
		boolean existingMovie = false;
		
		Iterable<Movie> movieList = movieRepository.findAll();
		
		for(Movie movie: movieList) {
			if(movie.getTitle().equals(name))
				existingMovie = true;
		}
		
		return existingMovie;
	}
}
