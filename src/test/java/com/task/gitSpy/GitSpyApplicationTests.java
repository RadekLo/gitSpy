package com.task.gitSpy;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.gitSpy.Holiday.api.impl.HolidayAPIConsumer;
import com.task.gitSpy.Holiday.model.HolidayQueryParams;
import com.task.gitSpy.Holiday.model.HolidayResponse;
import com.task.gitSpy.Mocks.GitHubMock;
import com.task.gitSpy.Mocks.HolidayMock;
import com.task.gitSpy.gitHub.api.impl.GitHubAPIConsumer;
import com.task.gitSpy.gitHub.model.GitHubCommitQueryParams;
import com.task.gitSpy.gitHub.model.GitHubCommitResponse;
import com.task.gitSpy.model.GitSpyCommitsResponse;
import com.task.gitSpy.rest.GitSpyRestControllerIf;
import com.task.gitSpy.rest.impl.GitSpyRestController;

@RunWith(SpringRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GitSpyApplicationTests {

	@Test
	public void contextLoads() {
	}
		@InjectMocks
		GitSpyRestController gitSpyRestController;
		
	    @Mock
	    private HolidayAPIConsumer holidayApiConsumer;

	    @Mock
	    private GitHubAPIConsumer gitHubAPIConsumer;
	    
	    private ObjectMapper mapper = new ObjectMapper();

	    @BeforeMethod
	    public void setUp() {
	        MockitoAnnotations.initMocks(this);
	    }
	    
	    @Test
	    public void testGitSpyUserBadInput() throws IOException {

	    	Map<String, String> params= new HashMap<>();
	    	testUserInput(params);
	    	params.put("year","2018");
	    	testUserInput(params);
	    	params.put("country","PL");
	    	testUserInput(params);
	    	params.put("year","201q8");
	    	testUserInput(params);
	    	params.put("year","2018");
	    	params.put("gitHubUser","RadekLo");
	    	GitSpyCommitsResponse response = gitSpyRestController.getCommits(params);
	        Assert.assertTrue(response != null, "Response must not be null");
	        Assert.assertTrue(response.getStatus() == 200, "Response code should be 200");
	        Assert.assertTrue(response.getError() == null, "Message must be empty");
	    }

	    @Test
	    public void testGitSpyRestCommit() throws IOException {

	    	Map<String, String> params = getParamsOk();
	    	Mockito.when(holidayApiConsumer.getHolidays(Matchers.<HolidayQueryParams>any())).thenReturn(HolidayMock.responseOk());
	    	Mockito.when(gitHubAPIConsumer.getRepos(Matchers.<String>any())).thenReturn(GitHubMock.responseRepoOk());
	    	Mockito.when(gitHubAPIConsumer.getCommits(Matchers.<String>any(), Matchers.<String>any(), Matchers.<GitHubCommitQueryParams>any())).thenReturn(GitHubMock.responseCommitOk());
	    	
	    	GitSpyCommitsResponse response = gitSpyRestController.getCommits(params);
	        
	        Assert.assertTrue(response != null, "Response is null");
	        Assert.assertTrue(response.getStatus() == 200, "Response code should be 200");
	        Assert.assertTrue(response.getError() == null, "Message must be empty");
	        Assert.assertTrue(response.getGitSpyDayComits().size() == 1, "must be commits from 1 day");
	        Assert.assertTrue(response.getGitSpyDayComits().get(0).getGitSpyCommits().size() == 12, "should be 12 commits. 2 repo with 6 commit");
	    }
	    @Test
	    public void testGitSpyRestCommitBadHolliday() throws IOException {
	    	Map<String, String> params = getParamsOk();
	    	Mockito.when(holidayApiConsumer.getHolidays(Matchers.<HolidayQueryParams>any())).thenReturn(HolidayMock.responseBad());
	    	Mockito.when(gitHubAPIConsumer.getRepos(Matchers.<String>any())).thenReturn(GitHubMock.responseRepoOk());
	    	Mockito.when(gitHubAPIConsumer.getCommits(Matchers.<String>any(), Matchers.<String>any(), Matchers.<GitHubCommitQueryParams>any())).thenReturn(GitHubMock.responseCommitOk());
	   
	    	 GitSpyCommitsResponse response = gitSpyRestController.getCommits(params);
		     Assert.assertTrue(response != null, "Response is null");
		     Assert.assertTrue(response.getStatus() == 400, "Response code is not 400");
		     Assert.assertTrue(response.getError() != null, "No error message");
	    }
	    
	    @Test
	    public void testGitSpyRestCommitBadRepo() throws IOException {
	    	Map<String, String> params = getParamsOk();
	    	Mockito.when(holidayApiConsumer.getHolidays(Matchers.<HolidayQueryParams>any())).thenReturn(HolidayMock.responseOk());
	    	Mockito.when(gitHubAPIConsumer.getRepos(Matchers.<String>any())).thenReturn(GitHubMock.responseRepoBad());
	    	Mockito.when(gitHubAPIConsumer.getCommits(Matchers.<String>any(), Matchers.<String>any(), Matchers.<GitHubCommitQueryParams>any())).thenReturn(GitHubMock.responseCommitOk());
	   
	    	 GitSpyCommitsResponse response = gitSpyRestController.getCommits(params);
		     Assert.assertTrue(response != null, "Response is null");
		     Assert.assertTrue(response.getStatus() == 404, "Response code is not 400");
		     Assert.assertTrue(response.getError() != null, "No error message");
	    }
	    
	    @Test
	    public void testGitSpyRestCommitBadCommit() throws IOException {
	    	Map<String, String> params = getParamsOk();
	    	Mockito.when(holidayApiConsumer.getHolidays(Matchers.<HolidayQueryParams>any())).thenReturn(HolidayMock.responseOk());
	    	Mockito.when(gitHubAPIConsumer.getRepos(Matchers.<String>any())).thenReturn(GitHubMock.responseRepoOk());
	    	Mockito.when(gitHubAPIConsumer.getCommits(Matchers.<String>any(), Matchers.<String>any(), Matchers.<GitHubCommitQueryParams>any())).thenReturn(GitHubMock.responseCommitBad());
	   
	    	 GitSpyCommitsResponse response = gitSpyRestController.getCommits(params);
		     Assert.assertTrue(response != null, "Response is null");
		     Assert.assertTrue(response.getStatus() == 404, "Response code is not 400");
		     Assert.assertTrue(response.getError() != null, "No error message");
	    }
	    
	    private void testUserInput(Map<String, String> params) throws IOException {
			
	    	Mockito.when(holidayApiConsumer.getHolidays(Matchers.<HolidayQueryParams>any())).thenReturn(HolidayMock.responseOk());
	    	Mockito.when(gitHubAPIConsumer.getRepos(Matchers.<String>any())).thenReturn(GitHubMock.responseRepoOk());
	    	Mockito.when(gitHubAPIConsumer.getCommits(Matchers.<String>any(), Matchers.<String>any(), Matchers.<GitHubCommitQueryParams>any())).thenReturn(GitHubMock.responseCommitOk());

	        GitSpyCommitsResponse response = gitSpyRestController.getCommits(params);
	        Assert.assertTrue(response != null, "Response is null");
	        Assert.assertTrue(response.getStatus() == 400, "Response code is not 400");
	        Assert.assertTrue(response.getError() != null, "No error message");
		}

		private Map<String, String> getParamsOk() {
			Map<String, String> params= new HashMap<>();
	    	params.put("year","2019");
	    	params.put("country","PL");
	    	params.put("gitHubUser","RadekLo");
			return params;
		}

}
