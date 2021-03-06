package epamlab;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import epamlab.interfaces.IElevator;
import epamlab.interfaces.IFloor;
import org.apache.log4j.Logger;


public class Elevator implements IElevator {

    Logger log = Logger.getLogger(getClass());
    private Lock lock;
    private Condition condition;
    private CountDownLatch countDownLatch;
    private IFloor[] floorsCondition;

    private int capacity;
    private volatile int currentFloor;
    private int maxFloor;
    private boolean directUp = true;
    private Set<Person> personInElevator;

    private int counter;
    private int arrivedPerson = 0;

    public Elevator(int capacity, int maxFloor, IFloor[] floorsCondition) {
        counter = 0;
        lock = new ReentrantLock();
        this.condition = lock.newCondition();
        countDownLatch = new CountDownLatch(0);
        this.floorsCondition = floorsCondition;
        this.capacity = capacity;
        this.maxFloor = maxFloor;
        directUp = true;
        currentFloor = 1;
        this.personInElevator = new HashSet<>();
    }

    @Override
    public int getCountArrivedPerson() {
        return arrivedPerson;
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public Lock getLock() {
        return lock;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }

    @Override
    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    @Override
    public boolean goToElevator(Person person) {
        boolean isInElevator = false;
        try {
            lock.lock();
            if (personInElevator.size() < capacity) {
                personInElevator.add(person);
                isInElevator = true;
                log.info("Person " + person + " in elevator");
            }
        } finally {
            lock.unlock();
        }
        return isInElevator;
    }

    @Override
    public void goOutElevator(Person person) {

        try {
            lock.lock();
            personInElevator.remove(person);
            log.info("Person " + person + " is arrive");
            arrivedPerson++;
        } finally {
            lock.unlock();
        }
    }

    private void move() {
        try {
            lock.lock();
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
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        while ((counter < maxFloor * 2) || !personInElevator.isEmpty()) {
            try {
                goOutFromElevator();
                goInFromElevator();
            } catch (InterruptedException e) {
                log.error(e);
            }
            move();
        }
        log.info("arrivedPerson--" + arrivedPerson);
        log.info("personInElevator " + personInElevator);
        log.info(Arrays.toString(floorsCondition));

    }

    private void goInFromElevator() throws InterruptedException {
        int oldCapacity = personInElevator.size();
        if (oldCapacity < capacity) {
            int countNeedPerson = capacity - oldCapacity;
            int countPersonInFloor = floorsCondition[currentFloor - 1].getTotalCountPersons();
            if (countPersonInFloor != 0) {
                if (countPersonInFloor >= countNeedPerson) {
                    countDownLatch = new CountDownLatch(countNeedPerson);
                } else
                    countDownLatch = new CountDownLatch(countPersonInFloor);
            }
            try {
                floorsCondition[currentFloor - 1].getLock().lock();
                floorsCondition[currentFloor - 1].getCondition().signalAll();
            } finally {
                floorsCondition[currentFloor - 1].getLock().unlock();
            }
            countDownLatch.await(1, TimeUnit.SECONDS);
            if (oldCapacity == personInElevator.size()) {
                counter++;
            } else {
                counter = 0;
            }
        }
    }

    private void goOutFromElevator() throws InterruptedException {
        countDownLatch = new CountDownLatch(personInElevator.size());
        try {
            lock.lock();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
        countDownLatch.await(1, TimeUnit.SECONDS);
    }
}
