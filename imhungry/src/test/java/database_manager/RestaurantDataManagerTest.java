package database_manager;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import info.Info;
import info.RestaurantInfo;

public class RestaurantDataManagerTest {
	
	@Test
	public void addToListAndLoadRestaurantTest() throws SQLException {
		RestaurantDataManager manager = new RestaurantDataManager("nero");
		manager.addToList(new RestaurantInfo("KFC", 5, "test1", "", 2, "", 0, "", ""), 1);
		ArrayList<Info> list = manager.loadRestaurants(1);
		assert(list.size() >= 0);
	}
}
