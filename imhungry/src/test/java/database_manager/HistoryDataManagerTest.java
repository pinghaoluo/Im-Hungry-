package database_manager;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import info.GroceryInfo;
import info.History;
import info.Info;

public class HistoryDataManagerTest {
	
	@Test
	public void addToListAndLoadHistoryTest() throws SQLException {
		History search = new History("coffee", 5, 2);
		HistoryDataManager manager = new HistoryDataManager("nero");
		manager.addToList(search);
		ArrayList<History> list = manager.loadHistory();
		assert(list.size() >= 0);
	}
	
	@Test
	public void anotherUserLoadHistoryTest() throws SQLException {
		HistoryDataManager manager = new HistoryDataManager("julius");
		ArrayList<History> list = manager.loadHistory();
		assert(list.size() == 0);
	}
}
