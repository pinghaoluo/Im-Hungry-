USE ImHungryDatabase;

DELETE FROM Restaurants WHERE PlaceID NOT LIKE '';

DELETE FROM Recipes WHERE RecipeID >= 0;

DELETE FROM History WHERE ID >= 0;

DELETE FROM GroceryList WHERE GroceryItem NOT LIKE '';