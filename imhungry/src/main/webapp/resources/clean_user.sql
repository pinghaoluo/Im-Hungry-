USE ImHungryDatabase;

DELETE FROM Restaurants WHERE PlaceID NOT LIKE '';

DELETE FROM Recipes WHERE RecipeID >= 0;

DELETE FROM History WHERE ID >= 0;

DELETE FROM GroceryList WHERE GroceryItem NOT LIKE '';

DELETE FROM Users WHERE Username NOT LIKE '';

INSERT INTO Users VALUES('nero', 'b2b9beb8a18c9a04ea2ef4aa48087eb035fab13b7c6e87c63da7f333777cf437');