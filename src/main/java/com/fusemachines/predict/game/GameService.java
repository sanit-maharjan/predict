package com.fusemachines.predict.game;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	public Game addNew(Game game) {
		if(StringUtils.isEmpty(game))
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

	public Game findBy(String id) {
		if(StringUtils.isEmpty(id))
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

	public Game updateGameScore(String id) {
		
	}
}
