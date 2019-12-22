package database_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import info.Info;
import info.RecipeInfo;

public class RecipeDataManager extends DataManager {
	
	public RecipeDataManager(String username) {
		super(username);
	}
	
	public void addToList(RecipeInfo recipe, int list) {
		int spoonID = recipe.spoonID;
		String name = recipe.name;
		int rating = recipe.rating;
		int prepTime = recipe.prepTime;
		int cookTime = recipe.cookTime;
		String ingredients = "";
		for(String ingredient : recipe.ingredients)
			ingredients += ingredient + "|||";
		String instructions = "";
		for(String instruction : recipe.instructions)
			instructions += instruction + "|||";
		String imageURL = recipe.imageURL;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(JDBC_CONNECTION);
			ps = conn.prepareStatement("SELECT * FROM Recipes WHERE SpoonID = " + spoonID);
			rs = ps.executeQuery();
			
			if(!rs.next()) {
				//Recipe not in database
				ps.close();
				ps = conn.prepareStatement("INSERT INTO Recipes(SpoonID, RecipeName, RecipeRating, PrepTime, CookTime, "
						+ "Ingredients, Instructions, ImageURL, InFavorites, InDoNotShow, InToExplore, User, Seq"
						+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
				ps.setInt(1, spoonID);
				ps.setString(2, name);
				ps.setInt(3, rating);
				ps.setInt(4, prepTime); 
			    ps.setInt(5, cookTime);	
			    ps.setString(6, ingredients);
			    ps.setString(7, instructions); 
			    ps.setString(8, imageURL);				
			    if(list == 1) {
			    	ps.setInt(9, 1); 
					ps.setInt(10, 0);
					ps.setInt(11, 0);		
			    } else if(list == 2) {
				   ps.setInt(9, 0); 
				   ps.setInt(10, 1);
				   ps.setInt(11, 0);		
			    } else if(list == 3) {
				   ps.setInt(9, 0); 
				   ps.setInt(10, 0);
				   ps.setInt(11, 1);
			    }
			    ps.setString(12, username);
			    ps.setInt(13, recipe.order);
			    ps.execute();
			    System.out.println("Recipe added to database.");
			    
			} else {
				//Recipe already in database
				PreparedStatement update = null;

				String filter = "WHERE SpoonID = " + spoonID + " AND User LIKE '" + username + "'";
				if(list == 1) {
					update = conn.prepareStatement("UPDATE Recipes SET InFavorites = ? " + filter);
					update.setInt(1, 1);
				} else if(list == 2) {
					update = conn.prepareStatement("UPDATE Recipes SET InDoNotShow = ? " + filter);
					update.setInt(1, 1);
				} else if(list == 3) {
					update = conn.prepareStatement("UPDATE Recipes SET InToExplore = ? " + filter);
					update.setInt(1, 1);
				}
				update.execute();
				System.out.println("Existed recipe added to list.");
			}

		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("sqle closing conn: " + sqle.getMessage());
			}
		}
	}
	
	
	public void removeFromList(int recipeID, int list) {
		Connection conn = null;
		PreparedStatement update = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String filter = "WHERE SpoonID = " + recipeID + " AND User = '" + username + "'";
		try {
			Class.forName("com.mysql.jdbc.Driver"); // get driver for database
			conn = DriverManager.getConnection(JDBC_CONNECTION);

			if(list == 1) {
				update = conn.prepareStatement("UPDATE Recipes SET InFavorites = ? " + filter);
				update.setInt(1, 0);
			} else if(list == 2) {
				update = conn.prepareStatement("UPDATE Recipes SET InDoNotShow = ? " + filter);
				update.setInt(1, 0);
			} else if(list == 3) {
				update = conn.prepareStatement("UPDATE Recipes SET InToExplore = ? " + filter);
				update.setInt(1, 0);
			}
			update.execute();
			System.out.println("Recipe removed from list but kept.");
		
			ps = conn.prepareStatement("SELECT InFavorites, InDoNotShow, InToExplore FROM Recipes " + filter);
			rs = ps.executeQuery();
			rs.next();
			int currFav = rs.getInt("InFavorites");
			int currNot = rs.getInt("InDoNotShow");
			int currExplore = rs.getInt("InToExplore");	
			if(currFav == 0 && currNot == 0 && currExplore == 0) {
				PreparedStatement delete = conn.prepareStatement("DELETE FROM Recipes " + filter);
				delete.execute();
				delete.close();
				System.out.println("Recipe removed.");
			}
		
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(update != null) {
					update.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle closing conn: " + sqle.getMessage());
			}
		}
	}
	

	public ArrayList<Info> loadRecipes(int list){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<Info> recipeList = new ArrayList<Info>();
		String filter = "AND User LIKE '" + username + "'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(JDBC_CONNECTION);
			
			if(list == 1) {
		    	ps = conn.prepareStatement("SELECT * FROM Recipes WHERE InFavorites = 1 " + filter);
			} else if(list == 2) {
				ps = conn.prepareStatement("SELECT * FROM Recipes WHERE InDoNotShow = 1 " + filter);
			} else if(list == 3) {
				ps = conn.prepareStatement("SELECT * FROM Recipes WHERE InToExplore = 1 " + filter);
			}
			rs = ps.executeQuery();
			System.out.println("Loading recipes.");
		   
			while(rs.next()) {
				String splitter = "\\|\\|\\|+";
				ArrayList<String> ingredients = new ArrayList<>();
				String[] ingredientsArray = rs.getString("Ingredients").split(splitter);
				for(String ingredient : ingredientsArray) ingredients.add(ingredient);
				ArrayList<String> instructions = new ArrayList<>();
				String[] instructionsArray = rs.getString("Instructions").split(splitter);
				for(String instruction : instructionsArray) instructions.add(instruction);
				
				RecipeInfo recipeToAdd = new RecipeInfo(
					rs.getString("RecipeName"),
					rs.getInt("RecipeRating"),
					rs.getInt("SpoonID"),
					rs.getInt("PrepTime"),
					rs.getInt("CookTime"),
					ingredients,
					instructions,
					rs.getString("ImageURL"));
				recipeToAdd.order = rs.getInt("Seq");
				recipeList.add(recipeToAdd);
			}
		} catch (SQLException sqle) {
			System.out.println("sqle: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle closing conn: " + sqle.getMessage());
			}
		}
		return recipeList;
	} 
}
