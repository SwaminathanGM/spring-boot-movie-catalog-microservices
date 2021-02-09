package com.swamicodes.movieinfoservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieSummary {
	
	private String imdbID;
	
	private String title;
	
	private String plot;
	
	private String imdbRating;

	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}

	public String getTitle() {
		return title;
	}

	@JsonProperty("Title")
	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlot() {
		return plot;
	}

	@JsonProperty("Plot")
	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}
	
}
