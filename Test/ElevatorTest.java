import static org.junit.Assert.*;

import org.junit.Test;

import epamlab.Elevator;

public class ElevatorTest {

	@Test
	public void test() {
		Thread thread = new Thread(new Elevator());
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
