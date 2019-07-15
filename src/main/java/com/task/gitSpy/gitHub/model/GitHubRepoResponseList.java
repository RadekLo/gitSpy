
package com.task.gitSpy.gitHub.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class GitHubRepoResponseList
{
	@Getter
	@Setter
	private List<GitHubRepoResponse>list;
	@Getter
	@Setter
	private String error;

}