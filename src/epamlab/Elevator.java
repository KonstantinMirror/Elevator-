package epamlab;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Elevator implements Runnable {

	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	Set<Person> personInElevator = new HashSet<>();
	
	public void addPersonToElevator(Person person) {
		try{
			lock.lock();
			personInElevator.add(person);
		}finally{
			lock.unlock();
		}
	}
	
	public void removePersonFromElevator(Person person) {
		try{
			lock.lock();
			personInElevator.remove(person);
		}finally{
			lock.unlock();
		}
	}
	
	
	
	private int capacity;
	public volatile static int currentFloor = 1;
	private int maxFloor = 10;
	private boolean directUp = true;
	
	
	
	
	public boolean goToElevator(Person person) {
		
		
		return false;
	}
	
	private void addToElevator(Person person){
		try{
			lock.lock();
			personInElevator.add(person);
			
		}finally{
			lock.unlock();
		}
	}
	
	public void goOutElevator(Person person){
		
			try {
				while(!(person.needFloor == currentFloor)){
				condition.await();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		removeFromElevator(person);
	}
	
	private void removeFromElevator(Person person){
		
		try{
			lock.lock();
			personInElevator.remove(person);
			System.out.println("Person "+ person +" is arrive");
		}finally{
			lock.unlock();
		}
	}
	

	

	public Lock getLock() {
		return lock;
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


	@Override
	public void run() {
		for (int i = 30; i > 0; i--) {
			System.out.println(currentFloor);
			try {
				Thread.sleep(200);
				move();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
}
