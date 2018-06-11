package com.fusemachines.predict.game;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
public class Game implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4259622572665949908L;

	private String id;
	private Round round;
	private Country home;
	private Country away;
	private int homeScore;
	private int awayScoure;
	private long kickOffTime;
	private Result result;

	@Id
	public String getId() {
		return id;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public Country getHome() {
		return home;
	}

	public void setHome(Country home) {
		this.home = home;
	}

	public Country getAway() {
		return away;
	}

	public void setAway(Country away) {
		this.away = away;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}

	public int getAwayScoure() {
		return awayScoure;
	}

	public void setAwayScoure(int awayScoure) {
		this.awayScoure = awayScoure;
	}

	public long getKickOffTime() {
		return kickOffTime;
	}

	public void setKickOffTime(long kickOffTime) {
		this.kickOffTime = kickOffTime;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

}
