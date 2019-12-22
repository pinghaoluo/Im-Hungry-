package database_manager;

import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataManager {
	
	//TODO: put shared constants and functions here
	protected static final String LIST_NAME_FAVORITES = "favorites";
	protected static final String LIST_NAME_DO_NOT_SHOW = "do not show";
	protected static final String LIST_NAME_TO_EXPLORE = "to explore";
	protected static final String JDBC_CONNECTION = "jdbc:mysql://localhost:3306/ImHungryDatabase?user=root&password=sbnew123&allowPublicKeyRetrieval=true&useSSL=false";
	
	protected String username;
	
	//each DataManager is specific to one user
	public DataManager(String username) {
		this.username = username;
	}

}
