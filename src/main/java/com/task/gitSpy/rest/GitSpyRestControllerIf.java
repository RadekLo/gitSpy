package com.task.gitSpy.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.task.gitSpy.model.GitSpyCommitsResponse;

public interface GitSpyRestControllerIf {

	public GitSpyCommitsResponse getCommits(@RequestParam Map<String, String> params);

}
