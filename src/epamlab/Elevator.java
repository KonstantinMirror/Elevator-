package epamlab;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Elevator implements Runnable {
	
	Lock lock = new ReentrantLock();
	Set<Person> personInElevator = new HashSet<>();

	private int capacity;
	private int currentFloor = 1;
	private int maxFloor = 10;
	private boolean directUp = true;

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public int getMaxFloor() {
		return maxFloor;
	}

	public void setMaxFloor(int maxFloor) {
		this.maxFloor = maxFloor;
	}

	public boolean isDirectUp() {
		return directUp;
	}

	public void setDirectUp(boolean directUp) {
		this.directUp = directUp;
	}

	public void move() {
		if (currentFloor < maxFloor && directUp) {
			currentFloor++;
			if (currentFloor == maxFloor) {
				directUp = false;
			}
		} else if (currentFloor > 1 && !directUp) {

			currentFloor--;
			if (currentFloor == 1) {
				directUp = true;
			}
		}
	}
	
	public void persoToElevator(Person person) {
		if(person.getCurrentFlor() == currentFloor){
		}
	}
	
	public void persoOutElevator(Person person) {
		if(person.getCurrentFlor() == currentFloor){
		}
	}

	@Override
	public void run() {
		for (int i = 30; i > 0; i--) {
			System.out.println(currentFloor);
			try {
				Thread.sleep(200);
				move();					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
