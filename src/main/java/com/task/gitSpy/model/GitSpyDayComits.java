package com.task.gitSpy.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* Class for keep all commits from specific day from all repos
*/
@ToString(callSuper=true, includeFieldNames=true)
public class GitSpyDayComits {
	
    @JsonProperty("name")
    @Getter
    @Setter
    private String name;

    @JsonProperty("date")
    @Getter
    @Setter
    private String date;
    
    @JsonProperty("gitSpyCommits")
    @Getter
    @Setter
    private List<GitSpyCommit> gitSpyCommits;
    
    public GitSpyDayComits(String name, String date) {
		this.name = name;
		this.date = date;
		gitSpyCommits = new ArrayList<GitSpyCommit>();
	}
    
    public void addNewCommit(GitSpyCommit gitSpyCommit)
    {
    	gitSpyCommits.add(gitSpyCommit);
    }
}
