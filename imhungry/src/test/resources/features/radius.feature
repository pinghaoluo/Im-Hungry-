Feature: List Management Page

Background:

	Given I visit the website and database is empty


Scenario: Radius input field exists
	Then the radius input field exists


Scenario: Radius input field default 5
	Then the radius input field should have value "5"
	
Scenario: Radius will be changed properly
	When I search for "fish" and expect "10" results
	And change radius to "10"
	Then the radius input field should have value "10"
	
Scenario: Edited Radius will affect search query results
	When I search for "fish" and expect "10" results
	And change radius to "1"
	And press "submit" button
	Then there will be less results than the using default radius
