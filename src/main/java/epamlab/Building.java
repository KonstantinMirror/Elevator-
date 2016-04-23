package epamlab;

import epamlab.interfaces.IElevator;
import epamlab.interfaces.IFloor;
import org.apache.log4j.Logger;


public class Building {
	Logger log = Logger.getLogger(getClass());

	public Building(IFloor[] floors, IElevator elevator) {
		this.floors = floors;
		this.elevator = elevator;
	}

	private IFloor[] floors;
	private IElevator elevator;

	public IFloor[] getFloors() {
		return floors;
	}

	public void waitElevator(Person person) throws InterruptedException {
		try {
			floors[person.currentFlor - 1].getLock().lock();
			while (true) {
				log.info("try set "  + person );
				log.info("------>"+elevator.getCurrentFloor());
				if (person.currentFlor == elevator.getCurrentFloor()) {
					log.info(".........");
					if (elevator.goToElevator(person)) {
						elevator.getCountDownLatch().countDown();
						break;
					}
				}
				floors[person.currentFlor - 1].getCondition().await();
			}
		} finally {
			floors[person.currentFlor - 1].getLock().unlock();
		}

	}

	public void goOutFromNeedFloor(Person person) throws InterruptedException {
		log.info("wait go Out Elevator");
		while (true) {
			try {
				elevator.getLock().lock();
				log.info("try out  "  + person);
				log.info( "elevator floor is "+  elevator.getCurrentFloor() );
				if (person.needFloor == elevator.getCurrentFloor()) {
					elevator.goOutElevator(person);
					elevator.getCountDownLatch().countDown();
					break;
				} else {
					elevator.getCountDownLatch().countDown();
					elevator.getCondition().await();
				}
			} finally {
				elevator.getLock().unlock();
			}
		}
	}
}
