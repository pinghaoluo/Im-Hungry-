Feature: Quick Access List

Background:

	Given I visit the website and database is empty
	
Scenario: "Quick Access List" found
	Then I should see the "Quick Access List"

Scenario: Search terms added to "Quick Access List"
	When I search for "pizza" and expect "5" results
	And press "submit" button
	Then I should see "pizza" in the "Quick Access List"
	
Scenario: "Quick Access List" go to Search Page
	When I search for "pizza" and expect "5" results
	And press "submit" button
	And press "Back to Search" button
	And press a previous search terms
	Then I should see the "Results" page
	
Scenario: Photo Collage for each iterm in "Quick Access List"
	When I search for "pizza" and expect "5" results
	And press "submit" button
	Then I should see "pizza" in the "Quick Access List"
	And the collage for "pizza" should be placed there