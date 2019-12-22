Feature: Grocery List

Background:

	Given I visit the website and database is empty

Scenario: "Add to Grocery" button found
	When I search for "chicken" and expect "5" results
	And press "submit" button
	And press a recipe
	Then I should see the "Add to Grocery" button

Scenario: "Display Grocery" button found
	When I search for "chicken" and expect "5" results
	And press "submit" button
	Then I should see the "Display Grocery" button

Scenario: Display functionality worked
	When I search for "chicken" and expect "5" results
	And press "submit" button
	And press "Display Grocery" button
	Then I will go to the Grocery Page

Scenario: Item addition functionality worked
	When I search for "chicken" and expect "5" results
	And press "submit" button
	And press a recipe
	And press "Add to Grocery" button
	And press "Back to Results" button
	And press "Display Grocery" button
	Then the item will be added to the grocery list


Scenario: Item deletetion found
	When I search for "chicken" and expect "5" results
	And press "submit" button
	And press "Display Grocery" button
	Then I should see the "Remove Item" button
	
Scenario: Item deletetion functionality worked
	When I search for "chicken" and expect "5" results
	And press "submit" button
	And press "Display Grocery" button
	And delete the first grocery item
	Then the item will be deleted from the grocery list
