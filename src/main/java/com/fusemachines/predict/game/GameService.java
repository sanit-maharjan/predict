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

	public Optional<Game> findById(String id) {
		return this.gameRepository.findById(id);
	}

	public List<Game> findAll() {
		return this.gameRepository.findAll();
	}

	public void removeBy(String id) {
		if (StringUtils.isEmpty(id))
			throw new IllegalArgumentException("id must not be empty");
		this.gameRepository.deleteById(id);
	}
}
