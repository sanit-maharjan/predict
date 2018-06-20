package com.fusemachines.predict.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fusemachines.predict.game.dto.GameScoreDto;
import com.fusemachines.predict.prediction.PredictionService;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private PredictionService predictionService;

	public Game addNew(Game game) {
		if (StringUtils.isEmpty(game))
			throw new IllegalArgumentException("game must not be empty");
		return this.gameRepository.insert(game);
	}

	public Game update(Game game, String id) {
		if (StringUtils.isEmpty(id))
			throw new IllegalArgumentException("id must not be empty");

		if (StringUtils.isEmpty(game))
			throw new IllegalArgumentException("game object can not be empty");

		game.setId(id);
		return this.gameRepository.save(game);
	}

	public Game findById(String id) {
		if (StringUtils.isEmpty(id))
			throw new IllegalArgumentException("id must not be empty");

		return this.gameRepository.findById(id).get();
	}

	public List<Game> findAll() {
		return this.gameRepository.findAll();
	}

	public void removeBy(String id) {
		if (StringUtils.isEmpty(id))
			throw new IllegalArgumentException("id must not be empty");
		this.gameRepository.deleteById(id);
	}

	public Game updateGameScore(GameScoreDto gameScoreDto) {
		if (StringUtils.isEmpty(gameScoreDto.getId()))
			throw new IllegalArgumentException("id must not be empty");
		Game game = this.gameRepository.findById(gameScoreDto.getId()).get();
		
		game.setHomeScore(gameScoreDto.getHomeScore());
		game.setAwayScore(gameScoreDto.getAwayScore());
		if(gameScoreDto.getHomeScore() > gameScoreDto.getAwayScore()) {
			game.setResult(Result.HOME);
		} else if(gameScoreDto.getHomeScore() < gameScoreDto.getAwayScore()) {
			game.setResult(Result.AWAY);
		} else {
			game.setResult(Result.DRAW);
		}
		
		game = this.gameRepository.save(game);

		this.predictionService.calculatePrediction(game);
		
		return game;
	}
	
	
	public Map<String, Game> getGamesMap() {
		List<Game> games = findAll();
		
		Map<String, Game> gamesMap = new HashMap<>();
		
		for (Game game : games) {
			gamesMap.put(game.getId(), game);
		}
		
		return gamesMap;
	}
}
