package com.fusemachines.predict.prediction;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusemachines.predict.exception.BadRequestException;
import com.fusemachines.predict.game.Game;
import com.fusemachines.predict.game.GameService;
import com.fusemachines.predict.game.Result;
import com.fusemachines.predict.prediction.dto.AddPredictionDto;

@Service
public class PredictionService {

	@Autowired
	private PredictionRepository predictionRepository;
	@Autowired
	private GameService gameService;
	
	public Prediction addPrediction(AddPredictionDto dto, String userId) {
		Game game = gameService.findById(dto.getGameId()).get();
		if(game.getKickOffTime() - new Date().getTime() < 3600000)
			throw new BadRequestException("Time for prediction for this game has passed.");
		
		Prediction prediction = new Prediction();
		prediction.setUserId(userId);
		prediction.setHomeScore(dto.getHomeScore());
		prediction.setAwayScore(dto.getAwayScore());
		prediction.setGameId(dto.getGameId());
		if(dto.getHomeScore() > dto.getAwayScore())
			prediction.setResult(Result.HOME);
		else if(dto.getAwayScore() > dto.getHomeScore())
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
		if(prediction == null) 
			throw new BadRequestException("Prediction for this game not found.");
		
		Game game = gameService.findById(dto.getGameId()).get();
		if(game.getKickOffTime() - new Date().getTime() < 3600000)
			throw new BadRequestException("Time for prediction for this game has passed.");
		
		prediction.setUserId(userId);
		prediction.setHomeScore(dto.getHomeScore());
		prediction.setAwayScore(dto.getAwayScore());
		prediction.setGameId(dto.getGameId());
		if(dto.getHomeScore() > dto.getAwayScore())
			prediction.setResult(Result.HOME);
		else if(dto.getAwayScore() > dto.getHomeScore())
			prediction.setResult(Result.AWAY);
		else 
			prediction.setResult(Result.DRAW);
		prediction.setRound(game.getRound());
		return prediction;		
	}
}
