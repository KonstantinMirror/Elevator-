package epamlab.interfaces;

import epamlab.Person;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


public interface IElevator extends Runnable {

    int getCurrentFloor();

    Lock getLock();

    Condition getCondition();

    CountDownLatch getCountDownLatch();

    boolean goToElevator(Person person);

    void goOutElevator(Person person);

    int getCountArrivedPerson();
}
