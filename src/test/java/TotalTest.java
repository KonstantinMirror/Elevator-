import epamlab.Building;
import epamlab.Person;
import epamlab.interfaces.IElevator;
import epamlab.interfaces.IFloor;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import epamlab.Elevator;
import epamlab.Floor;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class TotalTest {
    Logger log = Logger.getLogger(getClass());
    private List<Thread> listPersons;
    private IElevator elevator;
    private int COUNT_PERSON = 100;

    @Before
    public void initFloors() {
        int COUNT_FLOORS = 20;
        int CAPACITY_ELEVATOR = 10;
        IFloor[] floors = new IFloor[COUNT_FLOORS];
        for (int i = 0; i < COUNT_FLOORS; i++) {
            floors[i] = new Floor();
        }
        elevator = new Elevator(CAPACITY_ELEVATOR, COUNT_FLOORS, floors);
        Building building = new Building(floors, elevator);
        listPersons = new ArrayList<>();
        for (int i = 0; i < COUNT_PERSON; i++) {
            int needFlor = (int) ((Math.random() * 1000) % 20);
            int currentFloor = (int) ((Math.random() * 1000) % 20);
            if (currentFloor == 0) {
                currentFloor = 1;
            }
            if (needFlor == 0) {
                needFlor = 1;
            }
            String name = "" + i;
            listPersons.add(new Thread(new Person(name, currentFloor, needFlor, building)));
        }
    }

    @Test
    public void test() {
        for (Thread thread : listPersons) {
            thread.start();
        }
        Thread elevatorThread = new Thread(elevator);
        elevatorThread.start();
        try {
            elevatorThread.join();
            assertEquals(COUNT_PERSON,elevator.getCountArrivedPerson());
        } catch (InterruptedException e) {
            log.error(e);
        }
    }
}
