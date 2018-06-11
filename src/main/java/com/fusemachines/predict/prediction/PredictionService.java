package com.fusemachines.predict.prediction;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusemachines.predict.exception.BadRequestException;
import com.fusemachines.predict.game.Game;
import com.fusemachines.predict.game.GameService;
import com.fusemachines.predict.game.Result;
import com.fusemachines.predict.game.Round;
import com.fusemachines.predict.prediction.dto.AddPredictionDto;

@Service
public class PredictionService {
	
	private int G1_SCORE = 4;
	private int G1_RESULT = 1;
	
	private int G2_SCORE = 4;
	private int G2_RESULT = 1;
	
	private int G3_SCORE = 4;
	private int G3_RESULT = 1;
	
	private int PQF_SCORE = 5;
	private int PQF_RESULT = 2;
	
	private int QF_SCORE = 6;
	private int QF_RESULT = 3;
	
	private int SF_SCORE = 7;
	private int SF_RESULT = 5;
	
	private int F_SCORE = 10;
	private int F_RESULT = 5;

	@Autowired
	private PredictionRepository predictionRepository;
	@Autowired
	private GameService gameService;

	public Prediction addPrediction(AddPredictionDto dto, String userId) {
		Game game = gameService.findById(dto.getGameId());
		if (game.getKickOffTime() - new Date().getTime() < 3600000)
			throw new BadRequestException("Time for prediction for this game has passed.");

		Prediction prediction = new Prediction();
		prediction.setUserId(userId);
		prediction.setHomeScore(dto.getHomeScore());
		prediction.setAwayScore(dto.getAwayScore());
		prediction.setGameId(dto.getGameId());
		if (dto.getHomeScore() > dto.getAwayScore())
			prediction.setResult(Result.HOME);
		else if (dto.getAwayScore() > dto.getHomeScore())
			prediction.setResult(Result.AWAY);
		else
			prediction.setResult(Result.DRAW);
		prediction.setRound(game.getRound());
		return prediction;
	}

	public List<Prediction> getAllPredictionsByUserId(String userId) {
		return predictionRepository.findByUserId(userId);
	}

	public Prediction updatePrediction(AddPredictionDto dto, String userId) {
		Prediction prediction = predictionRepository.findByGameIdAndUserId(dto.getGameId(), userId);
		if (prediction == null)
			throw new BadRequestException("Prediction for this game not found.");

		Game game = gameService.findById(dto.getGameId());
		if (game.getKickOffTime() - new Date().getTime() < 3600000)
			throw new BadRequestException("Time for prediction for this game has passed.");

		prediction.setUserId(userId);
		prediction.setHomeScore(dto.getHomeScore());
		prediction.setAwayScore(dto.getAwayScore());
		prediction.setGameId(dto.getGameId());
		if (dto.getHomeScore() > dto.getAwayScore())
			prediction.setResult(Result.HOME);
		else if (dto.getAwayScore() > dto.getHomeScore())
			prediction.setResult(Result.AWAY);
		else
			prediction.setResult(Result.DRAW);
		prediction.setRound(game.getRound());
		return prediction;
	}
	
	public void calculatePrediction(Game game) {
		
		List<Prediction> predictions = this.predictionRepository.findAllByGameId(game.getId());
		for(Prediction prediction: predictions) {
			if(game.getHomeScore() == prediction.getHomeScore() && game.getAwayScore() == prediction.getAwayScore()) {
				
				prediction.setPoint(this.getPointsForRound("SCORE", game));
				
			} else if(game.getResult().equals(prediction.getResult())) {
				
				prediction.setPoint(this.getPointsForRound("RESULT", game));
				
			} else {
				prediction.setPoint(0);
			}
		}
		this.predictionRepository.saveAll(predictions);
	}
	
	private int getPointsForRound(String type, Game game) {
		if(type.equals("SCORE")) {
			
			if(game.getRound() == Round.G1)
				return G1_SCORE;
			
			if(game.getRound() == Round.G2)
				return G2_SCORE;
			
			if(game.getRound() == Round.G3)
				return G3_SCORE;
			
			if(game.getRound() == Round.PQF)
				return PQF_SCORE;
			
			if(game.getRound() == Round.QF)
				return QF_SCORE;
			
			if(game.getRound() == Round.SF)
				return SF_SCORE;
			
			if(game.getRound() == Round.F)
				return F_SCORE;
			
		} else if(type.equals("RESULT")) {
			
			if(game.getRound() == Round.G1)
				return G1_RESULT;
			
			if(game.getRound() == Round.G2)
				return G2_RESULT;
			
			if(game.getRound() == Round.G3)
				return G3_RESULT;
			
			if(game.getRound() == Round.PQF)
				return PQF_RESULT;
			
			if(game.getRound() == Round.QF)
				return QF_RESULT;
			
			if(game.getRound() == Round.SF)
				return SF_RESULT;
			
			if(game.getRound() == Round.F)
				return F_RESULT;
			
		}
		return 0;
	}
}
