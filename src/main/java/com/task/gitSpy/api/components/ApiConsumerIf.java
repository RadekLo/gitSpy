package com.task.gitSpy.api.components;

import java.io.IOException;

import com.task.gitSpy.model.ApiConsumerResponse;
import com.task.gitSpy.model.QueryParams;

public interface ApiConsumerIf {
	
	public ApiConsumerResponse getApiResponseAsString(String urlString,QueryParams queryParams) throws IOException;

}
