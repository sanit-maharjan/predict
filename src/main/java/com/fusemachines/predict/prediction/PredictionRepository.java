package com.fusemachines.predict.prediction;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PredictionRepository extends MongoRepository<Prediction, String> {

}
