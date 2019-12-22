Feature: Security
#Backlog 1
Background:

	Given I visit the website and database is empty
	
#domusaurea

Scenario: the software prevents SQL injection

	When I search for "', 0, 0); INSERT INTO GroceryList(GroceryItem) VALUES('test'); INSERT INTO Searches(Query, ResultsNum, Radius) VALUES('" and expect "5" results
    And press "submit" button
    And press "Display Grocery" button
	Then I should not see a grocery item #TODO


Scenario: user login button exists
	When press "Logout" button
	Then I should see a "Login" button
	
Scenario: user sign-up button exists
	When press "Logout" button
	Then I should see a "Sign Up" button

Scenario: user login button worked
	When press "Logout" button
	And press "Login" button
	Then I should see the "Login" page

Scenario: Sign up button worked
	When press "Logout" button
	And press "Sign Up" button
	Then I should see the "Sign Up" page
	
Scenario: Error Message Displayed
	When press "Logout" button
	And press "Login" button
	And login as "VanDarkholme" and "12450"
	Then I should see an error message

#Scenario: user login funtionality worked
#	When I clicked on the "login" button
#	And input a user-id and password
#	Then I will be directed back to the search page
#	And the user profile will be placed at top

	