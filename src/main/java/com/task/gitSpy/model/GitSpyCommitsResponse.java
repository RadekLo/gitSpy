package com.task.gitSpy.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.gitSpy.Holiday.model.Holiday;
import com.task.gitSpy.Holiday.model.Requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* Response class for answer from /api/commits
*/
@ToString(callSuper = true, includeFieldNames = true)
public class GitSpyCommitsResponse {

	@JsonProperty("status")
	@Getter
	@Setter
	private Integer status;
	
	@JsonProperty("user")
	@Getter
	@Setter
	private String user;

	@JsonProperty("year")
	@Getter
	@Setter
	private int year;

	@JsonProperty("country")
	@Getter
	@Setter
	private String country;

	@JsonProperty("error")
	@Getter
	@Setter
	private String error;

	@JsonProperty("gitSpyDayComits")
	@Getter
	@Setter
	private List<GitSpyDayComits> gitSpyDayComits;
}
