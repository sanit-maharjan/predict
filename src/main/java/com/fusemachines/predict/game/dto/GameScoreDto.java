/**
 * 
 */
package com.fusemachines.predict.game.dto;

/**
 * @author nalin
 *
 */
public class GameScoreDto {

	private String id;
	private int homeScore;
	private int awayScore;

	public GameScoreDto() {
		super();
	}

	public GameScoreDto(String id, int homeScore, int awayScore) {
		super();
		this.id = id;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}

	public int getAwayScore() {
		return awayScore;
	}

	public void setAwayScore(int awayScore) {
		this.awayScore = awayScore;
	}

}
