package com.task.gitSpy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true, includeFieldNames=true)
@AllArgsConstructor
public class GitSpyCommit {

	@JsonProperty("repo")
	@Getter
	@Setter
	private String repo;
	
	@JsonProperty("message")
	@Getter
	@Setter
	private String message;
}
