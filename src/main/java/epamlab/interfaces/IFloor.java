package epamlab.interfaces;

import epamlab.Person;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by konstantin on 23.04.2016.
 */
public interface IFloor {
    Lock getLock();

    Condition getCondition();

    int getTotalCountPersons();

    void addWaitPerson(Person person);

    void removeWaitPerson(Person person);
}
