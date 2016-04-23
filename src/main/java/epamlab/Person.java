package epamlab;

import org.apache.log4j.Logger;

public class Person implements Runnable {

    Logger log = Logger.getLogger(getClass());

    public final String name;
    public final int currentFlor;
    public final int needFloor;
    Building building;

    public Person(String name, int currentFlor, int needFloor, Building building) {
        this.name = name;
        this.currentFlor = currentFlor;
        this.needFloor = needFloor;
        this.building = building;
    }

    @Override
    public void run() {
        try {
            building.getFloors()[currentFlor - 1].addWaitPerson(this);
            building.waitElevator(this);
            building.getFloors()[currentFlor - 1].removeWaitPerson(this);
            building.goOutFromNeedFloor(this);
        } catch (InterruptedException e) {
            log.error(e);
        }
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", currentFlor=" + currentFlor + ", needFloor=" + needFloor + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (currentFlor != person.currentFlor) return false;
        if (needFloor != person.needFloor) return false;
        return !(name != null ? !name.equals(person.name) : person.name != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + currentFlor;
        result = 31 * result + needFloor;
        return result;
    }
}
