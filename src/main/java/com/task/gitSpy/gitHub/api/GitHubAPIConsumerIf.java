package com.task.gitSpy.gitHub.api;

import com.task.gitSpy.Holiday.model.HolidayResponse;
import com.task.gitSpy.Holiday.model.HolidayQueryParams;
import com.task.gitSpy.gitHub.model.GitHubCommitQueryParams;
import com.task.gitSpy.gitHub.model.GitHubCommitResponse;
import com.task.gitSpy.gitHub.model.GitHubCommitResponseList;
import com.task.gitSpy.gitHub.model.GitHubRepoResponse;
import com.task.gitSpy.gitHub.model.GitHubRepoResponseList;

import java.io.IOException;
import java.util.List;

/**
 * Interface for methods to consume the GitHub API
 */
public interface GitHubAPIConsumerIf {

	/**
	 * Get all commits for specific user on specific repo with filters defined in GitHubCommitQueryParams
	 * {@link HolidayResponse} object
	 *
	 * @param queryParams - the params encapsulated in an object
	 * @return an object representation of the response
	 * @throws IOException - when there are connection issues or the URL is
	 *                     malformed
	 */
	GitHubCommitResponseList getCommits(String user, String repo, GitHubCommitQueryParams queryParams) throws IOException;

	GitHubRepoResponseList getRepos(String user) throws IOException;
}
