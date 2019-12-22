DROP DATABASE IF EXISTS ImHungryDatabase;
CREATE DATABASE ImHungryDatabase;
USE ImHungryDatabase;

CREATE TABLE Users(
    Username VARCHAR(50) PRIMARY KEY,
    Password VARCHAR(500) NOT NULL
);

INSERT INTO Users VALUES('nero', 'b2b9beb8a18c9a04ea2ef4aa48087eb035fab13b7c6e87c63da7f333777cf437');

CREATE TABLE Restaurants (
    RestaurantID INT(11) PRIMARY KEY AUTO_INCREMENT,
    PlaceID VARCHAR(200) NOT NULL,
    RestaurantName VARCHAR(200) NOT NULL,
    RestaurantRating INT(11) NOT NULL,
    Address VARCHAR(200) NOT NULL,
    Price INT(11) NOT NULL,  -- note that the constructor takes an integer for price level
    DriveTimeText VARCHAR(200) NOT NULL,
    DriveTimeValue INT(11) NOT NULL,
    Phone VARCHAR(200) NOT NULL,
    Website VARCHAR(200) NOT NULL,
    InFavorites INT(2) NOT NULL,
    InDoNotShow INT(2) NOT NULL,
    InToExplore INT(2) NOT NULL,
    User VARCHAR(50) NOT NULL,
    Seq INT(11),
    FOREIGN KEY (User) REFERENCES Users(Username) ON DELETE CASCADE
);

CREATE TABLE Recipes(
    RecipeID INT(11) PRIMARY KEY AUTO_INCREMENT,
    SpoonID INT(11) NOT NULL,
    RecipeName VARCHAR(200) NOT NULL,
    RecipeRating INT(11) NOT NULL,
    PrepTime INT(11) NOT NULL,
    CookTime INT(11) NOT NULL,
    Ingredients VARCHAR(500) NOT NULL,
    Instructions VARCHAR(1000) NOT NULL,
    ImageURL VARCHAR(500) NOT NULL,
    InFavorites INT(2) NOT NULL,
    InDoNotShow INT(2) NOT NULL,
    InToExplore INT(2) NOT NULL,
    User VARCHAR(50) NOT NULL,
    Seq INT(11),
    FOREIGN KEY (User) REFERENCES Users(Username) ON DELETE CASCADE
);

CREATE TABLE History(
    ID INT(11) PRIMARY KEY AUTO_INCREMENT,
    hName VARCHAR(200) NOT NULL,
    hNumber INT(11) NOT NULL,
    Radius INT(11) NOT NULL,
    User VARCHAR(50) NOT NULL,
    FOREIGN KEY (User) REFERENCES Users(Username) ON DELETE CASCADE
);

CREATE TABLE GroceryList(
    GroceryItem VARCHAR(200) PRIMARY KEY,
    User VARCHAR(50) NOT NULL,
    FOREIGN KEY (User) REFERENCES Users(Username) ON DELETE CASCADE
);