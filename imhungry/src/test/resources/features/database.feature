Feature: Database
#Backlog 2
Background:

	Given I visit the website and database is empty

Scenario: Recipe information beyond just a single session
	When I search for "pizza" and expect "5" results
	And press "submit" button
	And press a recipe
	And select the list "Favorites"
	And press "Add to List" button
	And restart session
	And reload the page
	And I search for "pizza" and expect "5" results
	And press "submit" button
	And select the list "Favorites"
	And press "Manage List" button
	Then I should see an info item
	
Scenario: Restaurant information beyond just a single session
	When I search for "pizza" and expect "5" results
	And press "submit" button
	And press a restaurant
	And select the list "Favorites"
	And press "Add to List" button
	And restart session
	And reload the page
	And I search for "pizza" and expect "5" results
	And press "submit" button
	And select the list "Favorites"
	And press "Manage List" button
	Then I should see an info item

Scenario: Grocery List information beyond just a single session
	When I search for "pizza" and expect "5" results
	And press "submit" button
	And press a recipe
	And press "Add to Grocery" button
	And press "Back to Results" button
	And restart session
	And reload the page
	And I search for "pizza" and expect "5" results
	And press "submit" button
	And press "Display Grocery" button
	Then I should see the grocery list item

Scenario: Prior search term information beyond just a single session
	When I search for "cat" and expect "5" results
	And press "submit" button
	And restart session
	And reload the page
	Then I should see the prior search term

Scenario: User specific information
	When I search for "pizza" and expect "5" results
	And press "submit" button
	And press "Back to Search" button
	And press "Logout" button
	And press "Sign Up" button
	And Sign Up as "VanDarkholme" with "12450"
	Then I should not see the prior search term "pizza" by user A
