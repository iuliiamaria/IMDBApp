package imdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import imdb.entity.Movie;


@Repository
public interface MovieRepository extends CrudRepository<Movie, Long>{

		@Override
	    Iterable<Movie> findAll();
}
