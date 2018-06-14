package com.fusemachines.predict.prediction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.json.mgmt.users.User;
import com.fusemachines.predict.auth0.Auth0Service;
import com.fusemachines.predict.exception.BadRequestException;
import com.fusemachines.predict.game.Game;
import com.fusemachines.predict.game.GameService;
import com.fusemachines.predict.game.Result;
import com.fusemachines.predict.game.Round;
import com.fusemachines.predict.prediction.dto.AddPredictionDto;
import com.fusemachines.predict.prediction.dto.PointDto;

@Service
public class PredictionService {
	
	@Value(value = "${score.g1}")
	private int G1_SCORE;
	@Value(value = "${result.g1}")
	private int G1_RESULT;
	
	@Value(value = "${score.g2}")
	private int G2_SCORE;
	@Value(value = "${result.g2}")
	private int G2_RESULT;
	
	@Value(value = "${score.g3}")
	private int G3_SCORE;
	@Value(value = "${result.g3}")
	private int G3_RESULT;
	
	@Value(value = "${score.pqf}")
	private int PQF_SCORE;
	@Value(value = "${result.pqf}")
	private int PQF_RESULT;
	
	@Value(value = "${score.qf}")
	private int QF_SCORE;
	@Value(value = "${result.qf}")
	private int QF_RESULT;
	
	@Value(value = "${score.sf}")
	private int SF_SCORE;
	@Value(value = "${result.sf}")
	private int SF_RESULT;
	
	@Value(value = "${score.f}")
	private int F_SCORE;
	@Value(value = "${result.f}")
	private int F_RESULT;

	@Autowired
	private PredictionRepository predictionRepository;
	@Autowired
	private GameService gameService;

	public Prediction addPrediction(AddPredictionDto dto, String userId) {
		Prediction existingPrediction = predictionRepository.findByGameIdAndUserId(dto.getGameId(), userId);
		if (existingPrediction != null)
			throw new BadRequestException("You have already predicted for this game.");
		
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
		return predictionRepository.save(prediction);		

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
		
		prediction.setHomeScore(dto.getHomeScore());
		prediction.setAwayScore(dto.getAwayScore());
		if(dto.getHomeScore() > dto.getAwayScore())
			prediction.setResult(Result.HOME);
		else if (dto.getAwayScore() > dto.getHomeScore())
			prediction.setResult(Result.AWAY);
		else
			prediction.setResult(Result.DRAW);
		return predictionRepository.save(prediction);		
	}
	
	public List<Prediction> getAllPredictionsByGameId(String gameId) {
		Game game = gameService.findById(gameId);
		if(game.getKickOffTime() - new Date().getTime() > 3600000)
			return new ArrayList<>();
		return predictionRepository.findByGameId(gameId);

	}
	
	public void calculatePrediction(Game game) {
		
		List<Prediction> predictions = this.predictionRepository.findByGameId(game.getId());
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
	
	public List<PointDto> getAllPoints(Round round, Boolean paid) {
		List<User> users = Auth0Service.getAllUsers();
		
		List<PointDto> points = new ArrayList<>();
		for (User user : users) {

			Boolean feePaid = false;
			if (user.getAppMetadata() != null) {
				feePaid = (Boolean) user.getAppMetadata().get("paid");
			}

			if (feePaid != null && (feePaid == paid)) {
				List<Prediction> predictions = new ArrayList<>();

				if (round == null)
					predictions = predictionRepository.findByUserId(user.getId());
				else
					predictions = predictionRepository.findByRoundAndUserId(round, user.getId());

				int point = 0;
				for (Prediction prediction : predictions) {
					point += prediction.getPoint();
				}

				PointDto pointDto = PointDto.builder()
						.userId(user.getId())
						.username(user.getName())
						.point(point)
						.build();

				points.add(pointDto);
			}
		}
		
		Collections.sort(points, Comparator.comparing(PointDto::getPoint).reversed());

		return points;
	}
 }
