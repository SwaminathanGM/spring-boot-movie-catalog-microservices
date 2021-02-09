package com.swamicodes.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.swamicodes.moviecatalogservice.models.CatalogItem;
import com.swamicodes.moviecatalogservice.models.Movie;
import com.swamicodes.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;

//	@Autowired
//	private WebClient.Builder webClientBuilder;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		
		UserRating userRating = restTemplate.getForObject("https://rating-data-service/ratingsdata/users/"+userId, UserRating.class);
		return userRating.getRatingsList().stream().map(rating -> {
			Movie movie = restTemplate.getForObject("https://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			return new CatalogItem(movie.getMovieName(), movie.getOverview() , rating.getRating());
		}).collect(Collectors.toList());
	}
	
//	Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/" + rating.getMovieId()).retrieve()
//	.bodyToMono(Movie.class).block();
}
