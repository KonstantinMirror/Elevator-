import epamlab.Building;
import epamlab.Elevator;
import epamlab.Person;
import epamlab.interfaces.IFloor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ElevatorTest {

    Elevator elevator;
    IFloor[] floors;
    Person person;

    int CAPACITY_ELEVATOR = 10;
    int COUNT_FLOORS = 10;

    @Before
    public void init() {
        floors = new IFloor[COUNT_FLOORS];
        for (int i = 0; i < COUNT_FLOORS; i++) {
            floors[i] = mock(IFloor.class);
        }
        elevator = new Elevator(CAPACITY_ELEVATOR, COUNT_FLOORS, floors);
        Building building = mock(Building.class);
        person = new Person("Piter", 1, 5, building);
    }

    @Test
    public void testElevator() {
        assertTrue(elevator.goToElevator(person));
        elevator.goOutElevator(person);
        assertEquals(elevator.getCountArrivedPerson(), 1);
    }
}
