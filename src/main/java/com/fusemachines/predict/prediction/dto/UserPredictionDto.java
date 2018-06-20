package com.fusemachines.predict.prediction.dto;

import com.fusemachines.predict.game.Round;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPredictionDto {
	private String gameId;
	private String userId;
	private int homeScore;
	private int awayScore;
	private Round round;
}
