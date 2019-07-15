package com.task.gitSpy.gitHub.model;

import com.task.gitSpy.model.QueryParams;

public class GitHubCommitQueryParams extends QueryParams{

	static final  String START_HOUR = "T00:00:00Z";
	static final  String END_HOUR = "T23:59:59Z";
	
	public GitHubCommitQueryParams filterOneDay(String date)
	{
		since(date+START_HOUR);
		until(date+END_HOUR);
		return this;
	}
	
    public GitHubCommitQueryParams since(String since) {
        params.put(GitHubCommitAPIParameter.SINCE.toString(), since);
        return this;
    }

    public GitHubCommitQueryParams until(String from) {
        params.put(GitHubCommitAPIParameter.UNTIL.toString(), from);
        return this;
    }
    
    
    public enum GitHubCommitAPIParameter {
    	SINCE("since"),
        UNTIL("until");

        private String param;

    	GitHubCommitAPIParameter(String param) {
            this.param = param;
        }

        @Override
        public String toString() {
            return param;
        }
    }
}
