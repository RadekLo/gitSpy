package com.task.gitSpy.api.components.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.task.gitSpy.api.components.ApiConsumerIf;
import com.task.gitSpy.model.ApiConsumerResponse;
import com.task.gitSpy.model.QueryParams;

/**
* Class for connect to external serwice and receive response as json string and response code
*/
@Component
public class ApiConsumer implements ApiConsumerIf{
	private static final Logger log = LogManager.getLogger(ApiConsumer.class);
	
	@Override
	public ApiConsumerResponse getApiResponseAsString(String urlString,QueryParams queryParams) throws IOException {

		log.info("Url call {}", urlString + queryParams.queryString());

		URL url = new URL(urlString + queryParams.queryString());

		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.connect();

		StringBuilder builder = new StringBuilder();
		BufferedReader bufferedReader = null;
		String line = null;

		int responseCode = connection.getResponseCode();
		log.info("response code : {}", responseCode);

		if (responseCode == 200) {
			bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} else {
			bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
		}
		while ((line = bufferedReader.readLine()) != null) {
			builder.append(line);
		}
		bufferedReader.close();

		return new ApiConsumerResponse(responseCode, builder.toString());
	}
}
