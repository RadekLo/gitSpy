package com.task.gitSpy.Mocks;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.gitSpy.Holiday.model.HolidayResponse;
import com.task.gitSpy.gitHub.model.GitHubCommitResponse;
import com.task.gitSpy.gitHub.model.GitHubCommitResponseList;
import com.task.gitSpy.gitHub.model.GitHubRepoResponse;
import com.task.gitSpy.gitHub.model.GitHubRepoResponseList;
import com.task.gitSpy.model.ApiConsumerResponse;;

public abstract class GitHubMock {

	private static ObjectMapper mapper = new ObjectMapper();

	public static String RESPONSE_BAD = "{ \"message\": \"Not Found\",\"documentation_url\": \"https://developer.github.com/v3/repos/#list-user-repositories\"}";

	public static GitHubCommitResponseList responseCommitOk() throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		List<GitHubCommitResponse> commits = mapper.reader().forType(new TypeReference<List<GitHubCommitResponse>>() {
		}).readValue(new File("src\\test\\resources\\githubCommits.json"));

		GitHubCommitResponseList result = new GitHubCommitResponseList();
		result.setList(commits);

		return result;
	}

	public static GitHubCommitResponseList responseCommitBad() throws IOException {
		return new GitHubCommitResponseList(null, "Bad commit response");
	}

	public static GitHubRepoResponseList responseRepoOk() throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		List<GitHubRepoResponse> repos = mapper.reader().forType(new TypeReference<List<GitHubRepoResponse>>() {
		}).readValue(new File("src\\test\\resources\\githubRepo.json"));
		GitHubRepoResponseList result = new GitHubRepoResponseList();
		result.setList(repos);

		return result;
	}

	public static GitHubRepoResponseList responseRepoBad() throws IOException {
		return new GitHubRepoResponseList(null, "Bad repo response");
	}

}
