package epamlab;

import java.util.ArrayList;
import java.util.List;

import epamlab.interfaces.IElevator;
import epamlab.interfaces.IFloor;
import org.apache.log4j.Logger;


public class Main {

	public static void main(String[] args) {

		Logger log = Logger.getLogger(Main.class);
		int countFloors = 20;
		IFloor[] floors = new IFloor[countFloors];
		for (int i = 0; i < countFloors; i++) {
			floors[i] = new Floor();
		}

		IElevator elevator = new Elevator(8, countFloors, true, floors);
		Building building = new Building(floors, elevator);
		List<Thread> listPersons = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			int needFlor = (int) ((Math.random() * 1000) % 20);
			int currentFloor = (int) ((Math.random() * 1000) % 20);
			if(currentFloor==0){
				currentFloor=1;
			}
			if(needFlor==0){
				needFlor=1;
			}
			String name = "" + i;
			listPersons.add(new Thread(new Person(name, currentFloor, needFlor, building)));
		}

		for (Thread thread : listPersons) {
			thread.start();
		}
		Thread elevatorThread = new Thread(elevator);
		elevatorThread.start();
		try {
			elevatorThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
