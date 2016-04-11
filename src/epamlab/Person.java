package epamlab;

public class Person implements Runnable {
	
	 private String name;
	 private int currentFlor;
	 private int needFloor;
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCurrentFlor() {
		return currentFlor;
	}
	public void setCurrentFlor(int currentFlor) {
		this.currentFlor = currentFlor;
	}
	public int getNeedFloor() {
		return needFloor;
	}
	public void setNeedFloor(int needFloor) {
		this.needFloor = needFloor;
	}
	@Override
	public void run() {
		
		
	}
	 
	 
	 
}
