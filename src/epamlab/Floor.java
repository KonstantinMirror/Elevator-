package epamlab;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Floor {
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private Set<Person> personsWaitElevator = new HashSet<>();
	public Lock getLock() {
		return lock;
	}
	public Condition getCondition() {
		return condition;
	}
	
	public void addWaitPerson(Person person) {
		try{
			lock.lock();
			personsWaitElevator.add(person);
		}finally{
			lock.unlock();
		}
	}
	
	public void removeWaitPerson(Person person) {
		try{
			lock.lock();
			personsWaitElevator.remove(person);
		}finally{
			lock.unlock();
		}
	}
	
	
	
}
