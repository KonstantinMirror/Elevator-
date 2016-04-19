package epamlab;

import org.apache.log4j.Logger;

public class Person implements Runnable {
	
	Logger log = Logger.getLogger(getClass());
	
	 public final String name;
	 public final int currentFlor;
	 public final int needFloor;
	 Building building;
	 
	 
	public Person(String name, int currentFlor, int needFloor) {
		this.name = name;
		this.currentFlor = currentFlor;
		this.needFloor = needFloor;
	}


	@Override
	public void run() {
		try {
			building.getFloors()[currentFlor].addWaitPerson(this);
			building.waitElevator(this);
			building.getFloors()[currentFlor].removeWaitPerson(this);
			building.goOutFromNeedFloor(this);
		} catch (InterruptedException e) {
			log.error(e);
		}
		
		
		
	}
	 
	 
	 
}
