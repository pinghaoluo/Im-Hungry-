package info;

import java.util.ArrayList;

public class GroceryInfo extends Info {
	public String item;
	
	public GroceryInfo(String ingredient) {
		this.item = ingredient;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(!(other instanceof GroceryInfo)) return false;
		GroceryInfo otherGroceryInfo = (GroceryInfo) other;
		return this.item.equals(otherGroceryInfo.item);
	}

}
