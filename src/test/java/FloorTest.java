import epamlab.Building;
import epamlab.Floor;
import epamlab.Person;
import epamlab.interfaces.IFloor;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FloorTest {

    @Test
    public void testFloor() {
        IFloor floor = new Floor();
        Building building = mock(Building.class);
        Person person = new Person("Piter", 1, 5, building);
        floor.addWaitPerson(person);
        assertEquals(floor.getTotalCountPersons(), 1);
        floor.removeWaitPerson(person);
        assertEquals(floor.getTotalCountPersons(), 0);
    }
}
