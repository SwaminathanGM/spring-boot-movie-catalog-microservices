package com.swamicodes.moviecatalogservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.swamicodes.moviecatalogservice.models.CatalogItem;
import com.swamicodes.moviecatalogservice.models.Movie;
import com.swamicodes.moviecatalogservice.models.Rating;

@Service
public class MovieInfoService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getMovieName(), movie.getOverview() , rating.getRating());
	}
	
	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("No Movie", "" ,rating.getRating());
	}

}
