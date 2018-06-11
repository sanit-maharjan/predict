package com.fusemachines.predict.game;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

	@Autowired
	private GameService gameService;

	@PostMapping
	public Game addNew(@RequestBody Game game) {
		return this.gameService.addNew(game);
	}

	@PutMapping("/{id}")
	public Game update(@RequestBody Game game, @PathVariable("id") String id) {
		return this.gameService.update(game, id);
	}

	@GetMapping("/{id}")
	public Optional<Game> findBy(@PathVariable("id") String id) {
		return this.gameService.findBy(id);
	}

	@GetMapping
	public List<Game> findAll() {
		return this.gameService.findAll();
	}
	
	@DeleteMapping("/{id}")
	public void removeBy(@PathVariable("id") String id) {
		this.gameService.removeBy(id);
	}
}
