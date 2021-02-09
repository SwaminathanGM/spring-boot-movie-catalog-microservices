package com.swamicodes.movieinfoservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.swamicodes.movieinfoservice.models.Movie;
import com.swamicodes.movieinfoservice.models.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MovieResource {

	@Value("${api.key}")
	public String apiKey;

	@Autowired
	private RestTemplate restTemplate;
	
	private String omdbUrl = "http://www.omdbapi.com/?apikey=";

	@RequestMapping("/{movieId}")
	public Movie getMovieDetails(@PathVariable("movieId") String movieId) {
		String url = omdbUrl+ apiKey + "&i=" + movieId;
		MovieSummary movieSummary = restTemplate
				.getForObject(url, MovieSummary.class);
		return new Movie(movieSummary.getImdbID(), movieSummary.getTitle(), movieSummary.getPlot());
	}

}
