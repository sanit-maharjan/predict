package com.fusemachines.predict.prediction;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fusemachines.predict.config.AuthUtils;
import com.fusemachines.predict.game.Round;
import com.fusemachines.predict.prediction.dto.AddPredictionDto;
import com.fusemachines.predict.prediction.dto.PointDto;
import com.fusemachines.predict.prediction.dto.UserPredictionDto;

@RestController
@RequestMapping("/predictions")
public class PredictionController {

	@Autowired
	private PredictionService predictionService;
	@Autowired
	private AuthUtils authUtils;
	
	@PostMapping
	public Prediction addPrediction(@RequestBody @Valid AddPredictionDto dto) {
		return predictionService.addPrediction(dto, authUtils.getAuth0Id());
	}
		
	@GetMapping
	public List<Prediction> getAllPredictions() {
		return predictionService.getAllPredictionsByUserId(authUtils.getAuth0Id());
	}
	
	@PutMapping
	public Prediction updatePrediction(@RequestBody @Valid AddPredictionDto dto) {
		return predictionService.updatePrediction(dto, authUtils.getAuth0Id());
	}
	
	@GetMapping("game/{gameId}")
	public List<Prediction> getAllPredictionsByGameId(@PathVariable("gameId") String gameId) {
		return predictionService.getAllPredictionsByGameId(gameId);
	}
	
	@GetMapping("/points")
	public List<PointDto> getAllPoints(@RequestParam(value = "round", required = false) Round round,
			@RequestParam(value = "paid", required = false, defaultValue = "true") Boolean paid) {
		return predictionService.getAllPoints(round, paid);
	}
	
	@GetMapping("/game/{gameId}/download")
	public String downloadPredictions(@PathVariable String gameId, HttpServletResponse response) {
		return predictionService.convertPredictionsToCsv(gameId);
	}
	
	@GetMapping("/user/{userId}")
	public List<UserPredictionDto> getAllUserPredictions(@PathVariable String userId) {
		return predictionService.getAllUserPredictions(userId);
	}


}
