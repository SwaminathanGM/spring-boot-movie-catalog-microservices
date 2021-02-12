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
import com.swamicodes.moviecatalogservice.service.MovieInfoService;
import com.swamicodes.moviecatalogservice.service.UserRatingService;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MovieInfoService movieInfoService;

	@Autowired
	private UserRatingService userRatingService;

//	@Autowired
//	private WebClient.Builder webClientBuilder;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		UserRating userRating = userRatingService.getUserRating(userId);
		return userRating.getRatingsList().stream().map(rating -> movieInfoService.getCatalogItem(rating))
				.collect(Collectors.toList());
	}

//	Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/" + rating.getMovieId()).retrieve()
//	.bodyToMono(Movie.class).block();
}
