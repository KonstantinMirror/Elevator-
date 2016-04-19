package epamlab;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

public class Elevator implements Runnable {
	

	Logger log = Logger.getLogger(getClass());
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private int capacity;
	public volatile static int currentFloor = 1;
	private int maxFloor = 10;
	private boolean directUp = true;
	private Set<Person> personInElevator = new HashSet<>();
	private CountDownLatch countDownLatch;
	private Condition[] floorsCondition;

	public Lock getLock() {
		return lock;
	}

	public Condition getCondition() {
		return condition;
	}

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

	public boolean goToElevator(Person person) {
		boolean isInElevator = false;
		try {
			lock.lock();
			if (capacity < personInElevator.size()) {
				personInElevator.add(person);
				isInElevator = true;
				log.info("Person " + person + " in elevator");
			}
		} finally {
			lock.unlock();
		}
		return isInElevator;
	}

	public void goOutElevator(Person person) {

		try {
			lock.lock();
			personInElevator.remove(person);
			log.info("Person " + person + " is arrive");
		} finally {
			lock.unlock();
		}
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
				countDownLatch = new CountDownLatch(personInElevator.size());
				condition.signalAll();
				countDownLatch.await();
				floorsCondition[currentFloor].signalAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
}
