package com.fusemachines.predict.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusemachines.predict.game.dto.GameScoreDto;

@RestController
@RequestMapping("/games")
public class GameController {

	@Autowired
	private GameService gameService;

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public Game addNew(@RequestBody Game game) {
		return this.gameService.addNew(game);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Game update(@RequestBody Game game, @PathVariable("id") String id) {
		return this.gameService.update(game, id);
	}

	@GetMapping("/{id}")
	public Game findBy(@PathVariable("id") String id) {
		return this.gameService.findById(id);
	}

	@GetMapping
	public List<Game> findAll() {
		return this.gameService.findAll();
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void removeBy(@PathVariable("id") String id) {
		this.gameService.removeBy(id);
	}
	
	@PutMapping("/score")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Game updateGameScore(@RequestBody GameScoreDto gameScoreDto) {
		return this.gameService.updateGameScore(gameScoreDto);
	}
}
