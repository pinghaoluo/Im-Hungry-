package info;

import static org.junit.Assert.*;

import org.junit.Test;

public class GroceryInfoTest {

	@Test
	public void test() {
		GroceryInfo obj = new GroceryInfo("abc");
		assertEquals("abc",obj.item);
		GroceryInfo obj2 = new GroceryInfo("abc");
		GroceryInfo obj3 = new GroceryInfo("def");
		Object obj4 = null;
		assertEquals(false, obj.equals(obj4));
		assertEquals(true, obj.equals(obj));
		assertEquals(true, obj.equals(obj2));
		assertEquals(false, obj.equals(obj3));
	}

}
