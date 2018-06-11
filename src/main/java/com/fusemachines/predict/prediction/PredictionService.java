package com.fusemachines.predict.prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusemachines.predict.game.GameRepository;
import com.fusemachines.predict.game.GameService;
import com.fusemachines.predict.game.Result;
import com.fusemachines.predict.prediction.dto.AddPredictionDto;

@Service
public class PredictionService {

	@Autowired
	private PredictionRepository predictionRepository;
	@Autowired
	private GameService gameService;
	
	private Prediction addPrediction(AddPredictionDto dto) {
		Prediction prediction = new Prediction();
		prediction.setHomeScore(dto.getHomeScore());
		prediction.setAwayScore(dto.getAwayScore());
		if(dto.getHomeScore() > dto.getAwayScore())
			prediction.setResult(Result.HOME);
		else if(dto.getAwayScore() > dto.getHomeScore())
			prediction.setResult(Result.AWAY);
		else 
			prediction.setResult(Result.DRAW);
	}
}
