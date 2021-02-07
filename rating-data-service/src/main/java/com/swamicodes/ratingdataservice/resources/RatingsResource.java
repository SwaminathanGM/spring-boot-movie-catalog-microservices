package com.swamicodes.ratingdataservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamicodes.ratingdataservice.models.Rating;
import com.swamicodes.ratingdataservice.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {
	
	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId, 4);
	}
	
	@RequestMapping("users/{userId}") 
	public UserRating getUserRating() {
		UserRating userRating = new UserRating();
		List<Rating> ratingList = Arrays.asList(new Rating("1234", 4), new Rating("5678", 4));
		userRating.setRatingsList(ratingList);
		return userRating;
	}

}
