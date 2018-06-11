package com.fusemachines.predict.prediction;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PredictionRepository extends MongoRepository<Prediction, String> {

	List<Prediction> findByUserId(String userId);

	Prediction findByGameIdAndUserId(String gameId, String userId);

	List<Prediction> findByGameId(String gameId);

}
