package com.fusemachines.predict.prediction.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

public class AddPredictionDto {
	@Digits(integer=3, fraction=0, message = "Score is invalid.")
	@Min(value = 0, message = "Score is invalid.")
	private Integer homeScore;
	@Digits(integer=3, fraction=0, message = "Score is invalid.")
	@Min(value = 0, message = "Score is invalid.")
	private Integer awayScore;
	private String gameId;

	public int getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(Integer home) {
		this.homeScore = home;
	}

	public int getAwayScore() {
		return awayScore;
	}

	public void setAwayScore(Integer away) {
		this.awayScore = away;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

}
