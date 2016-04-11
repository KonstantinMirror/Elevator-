package epamlab;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Floor {
	private Lock lock  = new ReentrantLock();
	List<Person> persons = new ArrayList<>();
	
}
