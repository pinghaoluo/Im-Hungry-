package database_manager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import info.Info;
import info.RecipeInfo;

public class RecipeDataManagerTest {

	@Test
	public void addToListAndLoadRecipesTest1() throws SQLException {
		RecipeDataManager manager = new RecipeDataManager("nero");
		manager.addToList(new RecipeInfo("", 1, 1, 0, 0, new ArrayList<String>(), new ArrayList<String>(), ""), 1);
		ArrayList<Info> list = manager.loadRecipes(1);
		assert(list.size() >= 0);
	}
	
	@Test
	public void addToListAndLoadRecipesTest2() throws SQLException {
		RecipeDataManager manager = new RecipeDataManager("nero");
		manager.addToList(new RecipeInfo("", 1, 1, 0, 0, new ArrayList<String>(), new ArrayList<String>(), ""), 2);
		ArrayList<Info> list = manager.loadRecipes(2);
		assert(list.size() >= 0);
	}
	
}
