Feature: Pagination of Results

Background:

	Given I visit the website and database is empty
	
Scenario: "Next Page" button found
	When I search for "pizza" and expect "5" results
	And press "submit" button
	Then I should see the "Next Page" button

Scenario: Page number found
	When I search for "pizza" and expect "5" results
	And press "submit" button
	Then I should see the page number
	
Scenario: "Next Page" button is working
	When I search for "pizza" and expect "5" results
	And press "submit" button
	And go to next page
	Then I should go to the next page
#	
Scenario: "Previous Page" button found
	When I search for "pizza" and expect "5" results
	And press "submit" button
	Then I should see the "Previous Page" button
#	
Scenario: "Previous Page" button is working
	When I search for "pizza" and expect "5" results
	And press "submit" button
	And go to next page
	And go to previous page
	Then I should go back to the Previous Page