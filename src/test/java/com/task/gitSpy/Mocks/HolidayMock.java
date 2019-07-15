package com.task.gitSpy.Mocks;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.gitSpy.Holiday.model.HolidayResponse;
import com.task.gitSpy.gitHub.model.GitHubCommitResponse;
;

public abstract class HolidayMock {

    private static ObjectMapper mapper = new ObjectMapper();
  
    public static String RESPONSE_400 = "{\"status\":400,\"error\":\"Something went wrong on your end\"}";

    
    public static HolidayResponse responseOk() throws IOException {

        HolidayResponse users = mapper.reader()
          .forType(new TypeReference<HolidayResponse>() {})
          .readValue(new File("src\\test\\resources\\holidayMYDateResponse.json"));
        return users;
    }
    
    public static HolidayResponse responseBad() throws IOException {
        return mapper.readValue(RESPONSE_400, HolidayResponse.class);
    }
}
