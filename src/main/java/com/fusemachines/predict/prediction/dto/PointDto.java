package com.fusemachines.predict.prediction.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PointDto {
	private String userName;
	private int point;
}
