package com.task.gitSpy.Holiday.api;

import java.io.IOException;

import com.task.gitSpy.Holiday.model.HolidayResponse;
import com.task.gitSpy.Holiday.model.HolidayQueryParams;

public interface HolidayAPIConsumerIf {

	HolidayResponse getHolidays(HolidayQueryParams queryParams) throws IOException;
}
