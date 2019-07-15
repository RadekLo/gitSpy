package com.task.gitSpy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true, includeFieldNames=true)
@AllArgsConstructor
public class ApiConsumerResponse {
	
	@Getter
	@Setter
	int responseCode;
	@Getter
	@Setter
	String response;
}
