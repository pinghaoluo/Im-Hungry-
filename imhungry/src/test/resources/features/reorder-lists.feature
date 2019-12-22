Feature: Quick Access List

Background:

	Given I visit the website and database is empty
	
Scenario: "Move up" button found
	
	When I search for "cat" and expect "5" results
	And press "submit" button
	And press a recipe
	And select the list "Favorites"
	And press "Add to List" button
	And press "Back to Results" button
	And press "manage_list" button
	And select the list "Favorites"
	And press "manage_list" button
	Then I should see "Move up" button
	
Scenario: "Move down" button found
	When I search for "cat" and expect "5" results
	And press "submit" button
	And press "Manage List" button
	And select the list "Favorites"
	And press "manage_list" button
	Then I should see "Move down" button

Scenario: "Move up" button worked
	When I search for "pizza" and expect "5" results
	And press "submit" button
	And press a recipe
	And select the list "Favorites"
	And press "addtolist" button
	And press "backtoresults" button
	And select the list "Favorites"
	And press "Manage List" button
	And to press the "↑" button 
	Then the item will be moved up
#	
Scenario: "Move down" button worked
	When I search for "pizza" and expect "5" results
	And press "submit" button
	And select the list "Favorites"
	And press "Manage List" button
	And to press the "↓" button 
	Then the item will be moved down



