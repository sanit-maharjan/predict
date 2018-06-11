package com.fusemachines.predict.prediction;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusemachines.predict.prediction.dto.AddPredictionDto;

@RestController
@RequestMapping("/predictions")
public class PredictionController {

	@Autowired
	private PredictionService predictionService;
	
	@PostMapping
	public Prediction addPrediction(@RequestBody @Valid AddPredictionDto dto) {
		
		return null;
	}
}
