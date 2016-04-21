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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + currentFlor;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + needFloor;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (currentFlor != other.currentFlor)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return needFloor == other.needFloor;
    }
}
