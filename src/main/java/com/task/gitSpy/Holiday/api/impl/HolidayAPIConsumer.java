package com.task.gitSpy.Holiday.api.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.gitSpy.Holiday.api.HolidayAPIConsumerIf;
import com.task.gitSpy.Holiday.model.HolidayQueryParams;
import com.task.gitSpy.Holiday.model.HolidayResponse;
import com.task.gitSpy.api.components.ApiConsumerIf;
import com.task.gitSpy.model.ApiConsumerResponse;

@Component
public class HolidayAPIConsumer implements HolidayAPIConsumerIf {

	private static final Logger log = LogManager.getLogger(HolidayAPIConsumer.class);

	@Autowired
	ApiConsumerIf apiConsumer;

	@Value("${holidayApi.key}")
	private String holidayApiKey;

	@Value("${holidayApi.baseUri}")
	private String baseURl;

	@Override
	public HolidayResponse getHolidays(HolidayQueryParams queryParams) throws IOException {

		if (queryParams.getKey() == null) {
			queryParams.key(holidayApiKey);
		}
		ApiConsumerResponse apiResponse = apiConsumer.getApiResponseAsString(this.baseURl, queryParams);

		ObjectMapper mapper = new ObjectMapper();

		HolidayResponse response = mapper.readValue(apiResponse.getResponse(), HolidayResponse.class);

		return response;
	}
}
