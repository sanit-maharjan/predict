package com.fusemachines.predict.prediction;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusemachines.predict.config.AuthUtils;
import com.fusemachines.predict.prediction.dto.AddPredictionDto;

@RestController
@RequestMapping("/predictions")
public class PredictionController {

	@Autowired
	private PredictionService predictionService;
	@Autowired
	private AuthUtils authUtils;
	
	@PostMapping
	public Prediction addPrediction(@RequestBody @Valid AddPredictionDto dto) {
		predictionService.addPrediction(dto, authUtils.getAuth0Id());
		return null;
	}
		
	@GetMapping
	public List<Prediction> getAllPredictionsByUserId() {
		return predictionService.getAllPredictionsByUserId(authUtils.getAuth0Id());
	}
	
	@PutMapping
	public Prediction updatePrediction(@RequestBody @Valid AddPredictionDto dto) {
		predictionService.addPrediction(dto, authUtils.getAuth0Id());
		return null;
	}
}
