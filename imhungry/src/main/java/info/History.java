package info;

import java.util.ArrayList;

public class History {
	 public String query;
	 public int key;
	 public int number;
	 public int radius;
	 
	public History(String query) {
		this.query = query;
	}
	
	public History(String query,int number,int radius) {
		this.query = query;
		this.number = number;
		this.radius = radius;
	}
		
	public History(int key, String query,int number,int radius) {
		this.key = key;
		this.query = query;
		this.number = number;
		this.radius = radius;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(!(other instanceof History)) return false;
		History otherHistory = (History) other;
		return this.query.equals(otherHistory.query) && this.key == otherHistory.key && this.number == otherHistory.number &&
				this.radius == otherHistory.radius;
	}
}
