package epamlab;

public class Person implements Runnable {
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	 
	 
	 
}
