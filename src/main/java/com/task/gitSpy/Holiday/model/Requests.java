package com.task.gitSpy.Holiday.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "used",
        "available",
        "resets"
})
@ToString(callSuper=true, includeFieldNames=true)
public class Requests {
	@JsonProperty("used")
    @Getter
    @Setter
    private Long used;
	
	@JsonProperty("available")
    @Getter
    @Setter
    private Long available;
	
	@JsonProperty("resets")
	@Getter
    @Setter
	private String resets;
}
