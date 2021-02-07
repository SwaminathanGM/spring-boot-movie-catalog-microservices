package com.swamicodes.moviecatalogservice.models;

import java.util.List;

public class UserRating {
	
	private List<Rating> ratingsList;

	public List<Rating> getRatingsList() {
		return ratingsList;
	}

	public void setRatingsList(List<Rating> ratingsList) {
		this.ratingsList = ratingsList;
	}

}
