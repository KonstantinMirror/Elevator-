package epamlab;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

public class Floor {
    Logger log = Logger.getLogger(getClass());
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Set<Person> personsWaitElevator = new HashSet<>();

    public Lock getLock() {
        return lock;
    }
    public Condition getCondition() {
        return condition;
    }

    public int getTotalCountPersons() {
        try {
            lock.lock();
            return personsWaitElevator.size();
        } finally {
            lock.unlock();
        }
    }

    public void addWaitPerson(Person person) {
        log.info("add  waiting  in floor " + person);
        try {
            lock.lock();
            personsWaitElevator.add(person);
        } finally {
            lock.unlock();
        }
    }

    public void removeWaitPerson(Person person) {
        log.info("remove  waiting  in floor " + person);
        try {
            lock.lock();
            personsWaitElevator.remove(person);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Floor{" +
                "personsWaitElevator=" + personsWaitElevator +
                '}';
    }
}
