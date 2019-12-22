package user;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class UserTest {
	@Test
	//testing with a random string
	public void instantiationTest() { 
		Object i = new Object();
		User user = new User("nero","domusaurea");
		assertEquals("nero", user.uname);
		assertEquals("domusaurea", user.password);
	}
}
