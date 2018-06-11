package com.fusemachines.predict.prediction.dto;

import javax.validation.constraints.Pattern;

public class AddPredictionDto {
	@Pattern(regexp = "[\\s]*[0-9]*[1-9]+", message = "Home score is not valid")
	private int homeScore;
	@Pattern(regexp = "[\\s]*[0-9]*[1-9]+", message = "Away score is not valid")
	private int awayScore;
	private int gameId;

	public int getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(int home) {
		this.homeScore = home;
	}

	public int getAwayScore() {
		return awayScore;
	}

	public void setAwayScore(int away) {
		this.awayScore = away;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

}
