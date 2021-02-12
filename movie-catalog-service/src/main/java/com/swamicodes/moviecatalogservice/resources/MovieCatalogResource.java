package com.swamicodes.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.swamicodes.moviecatalogservice.models.CatalogItem;
import com.swamicodes.moviecatalogservice.models.Movie;
import com.swamicodes.moviecatalogservice.models.Rating;
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
		UserRating userRating = getUserRating(userId);
		return userRating.getRatingsList().stream().map(rating -> getCatalogItem(rating)).collect(Collectors.toList());
	}

	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	private CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getMovieName(), movie.getOverview() , rating.getRating());
	}

	@HystrixCommand(fallbackMethod = "getFallbackUserRating")
	private UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId, UserRating.class);
	}
	
	private UserRating getFallbackUserRating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setRatingsList(Arrays.asList(new Rating("0",0)));
		return userRating;
	}
	
//	Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/" + rating.getMovieId()).retrieve()
//	.bodyToMono(Movie.class).block();
	public List<CatalogItem> getFallbackCatalogItem(@PathVariable("userId") String userId) {
		return Arrays.asList(new CatalogItem("No Movie", "" ,0));
	}
}
