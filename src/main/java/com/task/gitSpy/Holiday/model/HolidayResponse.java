package com.task.gitSpy.Holiday.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "error",
        "holidays"
})
@ToString(callSuper=true, includeFieldNames=true)
public class HolidayResponse {

    @JsonProperty("status")
    @Getter
    @Setter
    private Integer status;

    @JsonProperty("holidays")
    @Getter
    @Setter
    private List<Holiday> holidays;

    @JsonProperty("requests")
    @Getter
    @Setter
    private Requests requests;
    
    @JsonProperty("error")
    @Getter
    @Setter
    private String error;

}

