package epamlab;

public class Building {
	
	 public Building(Floor[] floors, Elevator elevator) {
		this.floors = floors;
		this.elevator = elevator;
	}
	 


	private Floor[] floors;
	private Elevator elevator;
	public Floor[] getFloors() {
		return floors;
	}
	
	

	public void waitElevator(Person person) throws InterruptedException {
		while (true) {
			if (person.currentFlor == Elevator.currentFloor) {
				if(elevator.goToElevator(person)){
					break;
				}
			}else {
				floors[person.currentFlor].getCondition().await();
			}
		}
	}
	 
	public void goOutFromNeedFloor(Person person) {
		
	}
	 

}
