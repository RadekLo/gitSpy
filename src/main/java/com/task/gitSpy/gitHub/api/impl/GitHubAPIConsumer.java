package com.task.gitSpy.gitHub.api.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.gitSpy.api.components.ApiConsumerIf;
import com.task.gitSpy.gitHub.api.GitHubAPIConsumerIf;
import com.task.gitSpy.gitHub.model.GitHubCommitQueryParams;
import com.task.gitSpy.gitHub.model.GitHubCommitResponse;
import com.task.gitSpy.gitHub.model.GitHubCommitResponseList;
import com.task.gitSpy.gitHub.model.GitHubRepoResponse;
import com.task.gitSpy.gitHub.model.GitHubRepoResponseList;
import com.task.gitSpy.model.ApiConsumerResponse;
import com.task.gitSpy.model.QueryParams;

@Component
public class GitHubAPIConsumer implements GitHubAPIConsumerIf {

	private static final Logger log = LogManager.getLogger(GitHubAPIConsumer.class);


	@Autowired
	ApiConsumerIf apiConsumer;
	
	@Value("${gitHub.baseUriUsers}")
	private String baseUriUsers;

	@Value("${gitHub.baseUriRepos}")
	private String baseUriRepos;

	@Value("${gitHub.tokenUser}")
	private String tokenUser;

	@Value("${gitHub.token}")
	private String token;

	@Override
	public GitHubCommitResponseList getCommits(String user, String repo, GitHubCommitQueryParams queryParams)
			throws IOException {

		String connectionUrl = baseUriRepos + "/" + user + "/" + repo + "/commits" + queryParams.queryString();
		if (!token.isEmpty() && !tokenUser.isEmpty()) {
			connectionUrl = connectionUrl.replace("https://", "https://" + tokenUser + ":" + token + "@");
		}
	
		ApiConsumerResponse apiConsumerResponse = apiConsumer.getApiResponseAsString(connectionUrl, queryParams);
		
		
		GitHubCommitResponseList result = new GitHubCommitResponseList();
		
		ObjectMapper mapper = new ObjectMapper();
		if (apiConsumerResponse.getResponseCode() == 200) {
			List<GitHubCommitResponse> myObjects = mapper.reader().forType(new TypeReference<List<GitHubCommitResponse>>() {
			}).readValue(apiConsumerResponse.getResponse());
			result.setList(myObjects);
		} else {
			Map<String, String> errorMap = mapper.readValue(apiConsumerResponse.getResponse(), Map.class);
			if (errorMap.containsValue("message")) {
				result.setError(errorMap.get("message"));
			} else {
				result.setError(apiConsumerResponse.getResponse());
			}
		}

		return result;
	}

	@Override
	public GitHubRepoResponseList getRepos(String user) throws IOException {

		String connectionUrl = baseUriUsers + "/" + user + "/repos";
		if (!token.isEmpty() && !tokenUser.isEmpty()) {
			connectionUrl = connectionUrl.replace("https://", "https://" + tokenUser + ":" + token + "@");
		}

		ApiConsumerResponse apiConsumerResponse = apiConsumer.getApiResponseAsString(connectionUrl, new QueryParams());
		
		GitHubRepoResponseList result = new GitHubRepoResponseList();
		ObjectMapper mapper = new ObjectMapper();
		if (apiConsumerResponse.getResponseCode() == 200) {

			List<GitHubRepoResponse> myObjects = mapper.reader().forType(new TypeReference<List<GitHubRepoResponse>>() {
			}).readValue(apiConsumerResponse.getResponse());
			result.setList(myObjects);
		} else {
			Map<String, String> errorMap = mapper.readValue(apiConsumerResponse.getResponse(), Map.class);
			if (errorMap.containsValue("message")) {
				result.setError(errorMap.get("message"));
			} else {
				result.setError(apiConsumerResponse.getResponse());
			}
		}
		return result;
	}
}
