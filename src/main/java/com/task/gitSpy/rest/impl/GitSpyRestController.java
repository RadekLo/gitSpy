package com.task.gitSpy.rest.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.gitSpy.Holiday.api.HolidayAPIConsumerIf;
import com.task.gitSpy.Holiday.api.impl.HolidayAPIConsumer;
import com.task.gitSpy.Holiday.model.Holiday;
import com.task.gitSpy.Holiday.model.HolidayQueryParams;
import com.task.gitSpy.Holiday.model.HolidayResponse;
import com.task.gitSpy.gitHub.api.GitHubAPIConsumerIf;
import com.task.gitSpy.gitHub.model.GitHubCommitQueryParams;
import com.task.gitSpy.gitHub.model.GitHubCommitResponse;
import com.task.gitSpy.gitHub.model.GitHubCommitResponseList;
import com.task.gitSpy.gitHub.model.GitHubRepoResponse;
import com.task.gitSpy.gitHub.model.GitHubRepoResponseList;
import com.task.gitSpy.model.GitSpyCommit;
import com.task.gitSpy.model.GitSpyCommitsResponse;
import com.task.gitSpy.model.GitSpyDayComits;
import com.task.gitSpy.model.RestCommitRequestParam;
import com.task.gitSpy.rest.GitSpyRestControllerIf;

@RestController
@RequestMapping("/api")
public class GitSpyRestController implements GitSpyRestControllerIf {

	@Autowired
	HolidayAPIConsumerIf holidayAPIConsumer;

	@Autowired
	GitHubAPIConsumerIf gitHubAPIConsumer;
	private static ObjectMapper mapper = new ObjectMapper();

	@GetMapping("/test")
	public String test() {
		return "hello";
	}

	public static HolidayResponse responseMyHoliday200() throws IOException {

		HolidayResponse users = mapper.reader().forType(new TypeReference<HolidayResponse>() {
		}).readValue(new File("src\\test\\resources\\holidayMYDateResponse.json"));
		return users;
	}
	/**
	   * Main rest method to get all commits from all repos for holiday days from country in spoecific year
	   * @param params must have: year=YYYY&country=Country designation (check holiday api)&gitHubUser=USER
	   * @return GitSpyCommitsResponse
	   */
	@GetMapping("/commits")
	public GitSpyCommitsResponse getCommits(@RequestParam Map<String, String> params) {
		RestCommitRequestParam parseRequestParam = new RestCommitRequestParam(params);
		GitSpyCommitsResponse gitSpyCommitsResponse = new GitSpyCommitsResponse();
		gitSpyCommitsResponse.setCountry(parseRequestParam.getCountry());
		gitSpyCommitsResponse.setUser(parseRequestParam.getGitHubUser());
		gitSpyCommitsResponse.setYear(parseRequestParam.getYear());

		if (parseRequestParam.getStatus() == HttpStatus.BAD_REQUEST.value()) {
			gitSpyCommitsResponse.setStatus(parseRequestParam.getStatus());
			gitSpyCommitsResponse.setError(parseRequestParam.getError());
			return gitSpyCommitsResponse;
		}
		HolidayQueryParams queryParams = new HolidayQueryParams();
		queryParams.country(parseRequestParam.getCountry()).year(parseRequestParam.getYear());

		try {
			HolidayResponse holidayResponse = holidayAPIConsumer.getHolidays(queryParams);
			if (holidayResponse.getStatus() != 200) {
				gitSpyCommitsResponse.setStatus(holidayResponse.getStatus());
				gitSpyCommitsResponse.setError(holidayResponse.getError());
				return gitSpyCommitsResponse;
			} else {
				try {
					getCommitsForHolidays(parseRequestParam, gitSpyCommitsResponse, holidayResponse.getHolidays());
				} catch (HttpClientErrorException e) {
					gitSpyCommitsResponse.setStatus(e.getRawStatusCode());
					gitSpyCommitsResponse.setError(e.getMessage());
					return gitSpyCommitsResponse;
				}
			}
			return gitSpyCommitsResponse;
		} catch (IOException e) {
			gitSpyCommitsResponse.setStatus(400);
			gitSpyCommitsResponse.setError(e.getMessage());
		}
		return gitSpyCommitsResponse;
	}

	private void getCommitsForHolidays(RestCommitRequestParam parseRequestParam, GitSpyCommitsResponse myResponse,
			List<Holiday> holidays) throws IOException {
		GitHubRepoResponseList repos = gitHubAPIConsumer.getRepos(parseRequestParam.getGitHubUser());
		if( repos.getError()!= null )
		{
			myResponse.setStatus(404);
			myResponse.setError(repos.getError());
			return;
		}
		List<GitSpyDayComits> gitSpyDayComitsList = new ArrayList<GitSpyDayComits>();
		for (Holiday h : holidays) {
			GitHubCommitQueryParams gitHubCommitQueryParams = new GitHubCommitQueryParams();
			gitHubCommitQueryParams.filterOneDay(h.getDate());

			GitSpyDayComits actualDay = new GitSpyDayComits(h.getName(), h.getDate());
			for (GitHubRepoResponse repo : repos.getList()) {
				GitHubCommitResponseList commits = gitHubAPIConsumer.getCommits(
						parseRequestParam.getGitHubUser(), repo.getName(), gitHubCommitQueryParams);
				if( commits.getError()!= null )
				{
					myResponse.setStatus(404);
					myResponse.setError(commits.getError());
					return;
				}
				for (GitHubCommitResponse gitHubCommitResponse : commits.getList()) {
					actualDay.addNewCommit(new GitSpyCommit(repo.getName(),
							gitHubCommitResponse.getCommit().getMessage()));
				}
			}
			gitSpyDayComitsList.add(actualDay);
		}
		myResponse.setStatus(200);
		myResponse.setGitSpyDayComits(gitSpyDayComitsList);
	}
}
