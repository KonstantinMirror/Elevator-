import static org.junit.Assert.*;

import org.junit.Test;

import epamlab.Elevator;
import epamlab.Floor;

public class ElevatorTest {

	@Test
	public void test() {
		
		int countFloors =  10;
		Floor[] floors = new Floor[countFloors];
		for(int i =0;i<countFloors;i++){
			floors[i] = new Floor();
		}
		Thread elevatorThread = new Thread(new Elevator(7, countFloors, true, floors));
		elevatorThread.start();
		try {
			elevatorThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
