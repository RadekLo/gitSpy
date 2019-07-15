# gitSpy
**Description**
Test aplication, you use it on your own responsibility.
Program allow get all commits from all repo for specific user done in holidays from specific country.
Use https://holidayapi.com/ for getting holidays from specific country and year.
Commits get from gitHub.
**Usage**
1. Import to your IDE
2. Go to application.properties, and set holidayApi.key from https://holidayapi.com/ . This is mandatory
3. If you want you can also fill gitHub.token and gitHub.tokenUser using your github account user name and generated token.
	This give you bigger requests limit.
4. build and start as always.
5. Testing Url for request api
	localhost:8080/api/commits?year=2018&country=SomeCountry(see holiday allowed countries)&gitHubUser=SomeUser

Warning!
For now create separate query to serwer gitHub for each repo for each free day, what can very fast finish with
"API rate limit exceeded..." not solved.