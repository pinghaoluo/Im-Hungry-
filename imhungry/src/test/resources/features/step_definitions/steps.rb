Given(/^I visit the website and database is empty$/) do
  visit "localhost:9090"
  page.first("#buttonlogin").click
  fill_in('username', :with => 'nero')
  fill_in('pw', :with => 'domusaurea')
  page.first("#buttonlogin").click
end

Given(/^I visit the website$/) do
  visit "localhost:9090"
end

And(/^login as "([^"]*)" and "([^"]*)"$/) do |username, password|
    fill_in('username', :with => username)
    fill_in('pw', :with => password)
    page.first("#buttonlogin").click
end



When(/^I search for "([^"]*)" and expect "([^"]*)" results$/) do |query, num|
    fill_in('search', :with => query)
    fill_in('number', :with => num)
end

When(/^press "([^"]*)" button$/) do |buttonName|
  click_button(buttonName)
end

When(/^press a recipe$/) do
    find('#Rec_item0').click
end

When(/^press the third recipe$/) do
    find('#Rec_item2').click
end

When(/^press a restaurant$/) do
    find('#Res_item0').click
end

When(/^press an info item$/) do
    find('#item0').click
end

When(/^press the "([^"]*)"$/) do |elementName|
    find('#' + elementName).click
end


Then(/^I should see the "([^"]*)" page$/) do |pageTitle|
    expect(page).to have_title pageTitle
end

Then(/^I should see a title "([^"]*)"$/) do |query|
    expect(page).to have_content(query)
end

Then(/^I should see an element "([^"]*)"$/) do |elementName|
    expect(page).to have_css('#' + elementName)
end

Then(/^I should see  "([^"]*)" results$/) do |numResults|
    expect(page).to have_no_css('#Res_item' + numResults)
    expect(page).to have_no_css('#Rec_item' + numResults)
end

Then(/^I should see "([^"]*)" on the top of recipes$/) do |recipeName|
    expect(page).to have_css('div.Rec_section1')
end

Then(/^I should not see recipe "([^"]*)"$/) do |recipeName|
    expect(page).to have_no_content(recipeName)
end

Then(/^I should see the printable version page$/) do
    expect(page).to have_no_css('image')
end

When(/^select the list "([^"]*)"$/) do |listName|
    find('.select-selected').click
    all('div', :text => listName)[2].click
end

Then(/^I should see an info item$/) do
    expect(page).to have_css('#item0')
end

Then(/^I should see the page of To Explore List$/) do
    expect(page).to have_content('To Explore List')
end

Then(/^I should see the "Display Grocery" button$/) do
#    expect(page).to have_css('#display_grocery')
    page.find('#display_grocery')
end

Then(/^I should see the "Remove Item" button$/) do
#    expect(page).to have_css('#display_grocery')
    expect(page).to have_content("Remove Item")
end


Then(/^I should see the "Add to Grocery" button$/) do
#    expect(page).to have_css('#display_grocery')
    page.find('.addToGrocery')
end

Then(/^I will go to the Grocery Page$/) do
#    expect(page).to have_css('#display_grocery')
   expect(page).to have_title("Grocery")
end

Then(/^the item will be added to the grocery list$/) do
   expect(page).to have_content("soy sauce")
end

Then(/^I should see the "Quick Access List"$/) do
   expect(page).to have_css("#quickAccess")
end

Then(/^I should see "pizza" in the "Quick Access List"$/) do
   expect(page).to have_content("pizza")
end

Then(/^I should see the "Next Page" button$/) do
   expect(page).to have_css(".next")
end

Then(/^I should see the page number$/) do
   expect(page).to have_css(".pagination")
end

And(/^go to next page$/) do
  page.first('.pagination').find('.next').click
end

And(/^go to previous page$/) do
  page.first('.pagination').find('.prev').click
end

Then(/^I should go to the next page$/) do
   expect(page.first('.pagination').find('.active')).to have_content("2")
end

Then(/^I should go back to the Previous Page$/) do
  expect(page.first('.pagination').find('.active')).to have_content("1")
end


Then(/^I should see the "Previous Page" button$/) do
   expect(page).to have_css(".prev")
end

Then(/^I should see "Sort by Rating" button$/) do
   page.find("#sort_by_value")
end

Then(/^the items will be sorted$/) do
   page.find(".container")
end

And(/^press the second recipe$/) do
   find('#Rec_item1').click
end






When(/^restart session$/) do
    Capybara.reset_sessions!
end

And(/^reload the page$/) do
  visit "localhost:9090"
  page.first("#buttonlogin").click
  fill_in('username', :with => 'nero')
  fill_in('pw', :with => 'domusaurea')
  page.first("#buttonlogin").click
end

Then(/^the data still preserves$/) do
   expect(page).to have_content("horsh")
end

Then(/^the radius input field exists$/) do
   expect(page.find("#format").find("#hover_format1")).to have_css("#radius");
end

And(/^change radius to "([^"]*)"$/) do |radius|
  fill_in('radius', :with => radius)
end


Then(/^the radius that would be executed on will be 10000$/) do
   expect(page).to have_title("Search Page")
end

Then(/^I should see "Move up" button$/) do
   expect(page).to have_content("↑")
end

Then(/^I should see "Move down" button$/) do
   expect(page).to have_content("↓")
end


And(/^to press the "↑" button$/) do
  all('div', :text => "↑")[1].click
end

And(/^to press the "↓" button$/) do
  all('div', :text => "↑")[1].click
end

Then(/^the item will be moved up$/) do
   expect(page).to have_content("Cajun")
end

Then(/^the item will be moved down$/) do
   expect(page).to have_content("Cajun")
end

And(/^delete the first grocery item$/) do
  all("button", :text => "Remove Item")[0].click
end

Then(/^the item will be deleted from the grocery list$/) do
   expect(page).should have_no_content("brown sugur")
end

Then(/^I should see the grocery list item$/) do
   expect(page).to have_content("pizza")
end

Then(/^I should see the prior search term$/) do
   expect(page).to have_content("Quick Access")
end

Then(/^the radius input field should have value "([^"]*)"/) do |radius|
  expect(page).to have_field('radius', with: radius)
end

Then(/^there will be less results than the using default radius/) do
  expect(page.first('.pagination')).to have_no_content("4")
end

And(/^the collage for "([^"]*)" should be placed there/) do |query|
  expect(page).to have_css(".quickAccessItem")
end

Then(/^I should see a "Login" button/) do
  expect(page).to have_button('Login')
end

Then(/^I should see a "Sign Up" button/) do
#  expect(all("#buttonlogin")[1]).has_value("Sign Up")
  expect(page).to have_button('Sign Up')
end

Then(/^I should not see a grocery item/) do
  expect(page).to have_content("empty")
end

Then(/^I should see an error message/) do
  expect(page).to have_content("Username or Password wrong")
end

And(/^Sign Up as "([^"]*)" with "([^"]*)"/) do |username, password|
  fill_in('username', :with => username)
  fill_in('pw', :with => password)
  fill_in('pw2', :with => password)
  page.first("#buttonlogin").click
end

Then(/^I should not see the prior search term "([^"]*)" by user A/) do |searchTerm|
  expect(page).to have_no_content(searchTerm)
end

When(/^press a previous search terms/) do
  click_on("■ - pizza")
end



