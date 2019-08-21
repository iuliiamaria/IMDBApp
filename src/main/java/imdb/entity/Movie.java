package imdb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "json_movie")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="idmovie")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="release_year")
	private String release;
	
	@Column(name="genre")
	private String genre;
	
	@Column(name="movie_duration")
	private String time;
	
	@Column(name="director")
	private String director;
	
	@Column(name="stars")
	private String stars;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	
	public void setDirector(String director) {
		this.director = director;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}
	
}
