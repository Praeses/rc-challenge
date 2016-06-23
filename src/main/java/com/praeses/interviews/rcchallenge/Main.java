package com.praeses.interviews.rcchallenge;

import java.io.*;
import java.util.*;
import java.lang.System.*;
import java.lang.Integer;
import java.lang.Math;

public class Main {

    public static void main(String[] args) {
		
		World world = new World();
		
		ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
		
		//obstacles.add(new Obstacle(-1, world.getSizeY(), 0, -1));
		//obstacles.add(new Obstacle(-1, 0, world.getSizeX(), -1));
		//obstacles.add(new Obstacle(-1, world.getSizeY(), world.getSizeX() + 1, world.getSizeX()));
		//obstacles.add(new Obstacle(world.getSizeY(), world.getSizeY() + 1, world.getSizeX(), -1));
		
		for (int i = 0; i < world.getObstacleCount(); i++) {
		
			obstacles.add(new Obstacle(Integer.toString(i), world));
		
		}
		
		Car rcCar = new Car(world, obstacles);
		
    }
}

class Car {

	public int carX 		= 0;
	public int carY 		= 0;
	public String carDir 	= "north";
	int carGear 			= 1;
	public String heading	= "north";
	public boolean blocked	= "false";
	int[] gearBox			= {-1,1};
	String startXString		= "Please input the starting X Coordinate:";
	String startYString 	= "Please input the starting Y Coordinate:";
	String startDirString 	= "Please input the starting direction:";
	String startGearString 	= "Please input the starting gear:";
	String intError 		= "Please enter an integer!";
	String dirError 		= "Must be north, south, east, or west!";
	String gearError 		= "Must be -1 or 1";
	String badCom 			= "Unrecognized Command. Type 'help' for guidance.";
	static Scanner scInput;

	Car(World world, ArrayList<Obstacle> obstacles) {
		
		ignition();
		driverSeat(world, obstacles);
		
	}
	
	private void ignition() {
		
		boolean loop = true;
		
		scInput = new Scanner(System.in);
		
		do {
			try {
				System.out.println(startXString);
				carX = scInput.nextInt();
				loop = false;
   			} catch (Exception e) {
   					System.out.println(intError);
   					scInput.nextLine();
   			}
   		} while (loop == true);
   		loop = true;
   		
   		do {
			try {
				System.out.println(startYString);
				carY = scInput.nextInt();
				loop = false;
   			} catch (Exception e) {
   					System.out.println(intError);
   					scInput.nextLine();
   			}
   		} while (loop == true);
   		loop = true;
   		
   		do {
   			try {
				System.out.println(startDirString);
				carDir = scInput.next();
				switch (carDir) {
					case "north": case "south": case "east": case "west": 
						loop = false;
					default:
						System.out.println(dirError);
				}
			} catch (Exception e) {
   				System.out.println(dirError);
   				scInput.nextLine();
   			}
   		} while (loop == true);
   		loop = true;
   		
   		do {
   			try {
				System.out.println(startGearString);
				carGear = scInput.nextInt();
				switch (carGear) {
					case -1: case 1: 
						loop = false;
					default:
						System.out.println(gearError);
				}
			} catch (Exception e) {
   				System.out.println(gearError);
   				scInput.nextLine();
   			}
   		} while (loop == true);

	}
	
	private void driverSeat(World world, ArrayList<Obstacle> obstacles) {
	
		boolean loop = true;
		boolean loopB = true;	
		String drivingGuide = "TBD";
		String delimit = ",";
		String userInput = "init";	
		int arrayLength;
		int temp;
		
		do {
			do {
				try {
					scInput.nextLine();
					System.out.println("Drive!");
					userInput = scInput.next();
					loop = false;
				} catch (Exception e) {
					System.out.println("error");
					scInput.nextLine();
				}
				
			} while (loop == true);
			
			List<String> userInputList = Arrays.asList(userInput.split("\\s*,\\s*"));		
			arrayLength = userInputList.size();
			System.out.println("ArrayList size: " + arrayLength);
		
			for (int i = 0; i < arrayLength; i++) {
				System.out.println("ArrayList index of " + i + " is: " + ":" + userInputList.get(i) + ":");
			}
		
		
			if (arrayLength == 3) {				
				
				if (isInteger(userInputList.get(1), 10) == true && isInteger(userInputList.get(2), 10) == true) {
				
					steeringWheel(userInputList.get(0));
					transmission(Integer.parseInt(userInputList.get(1)));
					gasPedal(Integer.parseInt(userInputList.get(2)));
					
				} else {
				
					System.out.println("failThree: " + badCom);
				
				}
				
			} else if (arrayLength == 2) {
			
				if (userInputList.get(0).equals("gear") == true && isInteger(userInputList.get(1),10)) {
				
					transmission(Integer.parseInt(userInputList.get(1)));
					
				} else {
				
					System.out.println("failTwo: " + badCom);
				
				}
				
			} else if (arrayLength == 1) {
			
				if (userInputList.get(0).equals("north") == true || userInputList.get(0).equals("south") == true || userInputList.get(0).equals("east") == true || userInputList.get(0).equals("west") == true) {
				
					steeringWheel(userInputList.get(0));
					
				} else if (isInteger(userInputList.get(0), 10) == true) {
				
					gasPedal(Integer.parseInt(userInputList.get(0)));
					
				} else if (userInputList.get(0).equals("help") == true) {
				
					System.out.println(drivingGuide);
					
				} else if (userInputList.get(0).equals("exit") == true) {
				
					loopB = false;
					
				} else if (userInputList.get(0).equals("around the block") == true) {
				
				} else if (userInputList.get(0).equals("find obstacle") == true) {
			
				} else {
				
					System.out.println("failOne: " + badCom);
				
				}
				
			} else {
			
				System.out.println("fail: " + badCom);
			
			}
			
			dashboard();
			
		} while (loopB == true);
		
	}
	
	private void steeringWheel(String dir) {
	
		carDir = dir;
		setHeading();
	
	}
	
	private void transmission(int shifter) {
	
		carGear = shifter;
		setHeading();
	
	}
	
	private void setHeading() {
	
		if ((carDir.equals("north") && carGear == 1) || (carDir.equals("south") && carGear == -1)) {
		
			heading = "north";
		
		} else if ((carDir.equals("north") && carGear == -1) || (carDir.equals("south") && carGear == 1)) {
		
			heading = "south";
		
		} else if ((carDir.equals("east") && carGear == 1) || (carDir.equals("west") && carGear == -1)) {
		
			heading = "east";
		
		} else if ((carDir.equals("east") && carGear == -1) || (carDir.equals("west") && carGear == 1)) {
		
			heading = "west";
		
		}
	
	}
	
	private void gasPedal(int distance, StringArray<Obstacle> obstacles, World world) {
	
		int newCarX = carX;
		int newCarY = carY;
	
		if (carDir.equals("north") == true) {
		
			if (carGear > 0) {
				newCarY = carY - distance;
			} else if (carGear < 0) {
				newCarY = carY + distance;
			}
		
		} else if (carDir.equals("south") == true) {
		
			if (carGear > 0) {
				newCarY = carY + distance;
			} else if (carGear <0) {
				newCarY = carY - distance;
			}
		
		} else if (carDir.equals("east") == true) {
		
			if (carGear > 0) {
				newCarX = carX + distance;
			} else if (carGear < 0) {
				newCarX = carX - distance;
			}
		
		} else if (carDir.equals("west") == true) {
		
			if (carGear > 0) {
				newCarX = carX - distance;
			} else if (carGear < 0) {
				newCarX = carX + distance;
			}
		
		}
			
		int deltaY = newY - oldY;
		int deltaX = newX - oldX;
		int currentObstIndex = 0;
				
		if (deltaX != 0) {
		
			for (int j = 0; j < obstacles.getSize(); j++) {
				
				if (Math.max(Math.max(oldX, newX), obstacles.get(j).getWBound()) <= Math.min(Math.min(oldX, newX), obstacles.get(j).getEBound())) {
				
					if (Math.max(oldY, obstacles.get(j).getNBound()) <= Math.min(oldY, obstacles.get(j).getSBound())) {
					 
						if (heading.equals("east")) {
					
							newX = oldX + (newX - obstacles.get(j).getWBound());
							blocked = true;
							currentObstIndex = j;
					
						} else if (heading.equals("west")) {
					
							newX = oldX + (newX - obstacles.get(j).getEBound());
							blocked = true;
							currentObstIndex = j;
					
						}
						
					}
						
				}
				
			}
				
		}
				
		if (deltaY != 0) {
		
			for (int j = 0; j < obstacles.getSize(); j++) {
				
				if (Math.max(Math.max(oldY, newY), obstacles.get(j).getNBound()) <= Math.min(Math.min(oldY, newY), obstacles.get(j).getSBound())) {
				
					if (Math.max(oldX, obstacles.get(j).getWBound()) <= Math.min(oldX, obstacles.get(j).getEBound())) {
					 
						if (heading.equals("south")) {
					
							newY = oldY + (newY - obstacles.get(j).getNBound());
							blocked = true;
							currentObstIndex = j;
					
						} else if (heading.equals("north")) {
					
							newY = oldY + (newY - obstacles.get(j).getEBound());
							blocked = true;
							currentObstIndex = j;
					
						}
						
					}
						
				}
				
			}
				
		}
		
		if (newCarX >= world.getSizeX() || newCarX <= 0) {
		
			
		
		}
		
		carX = newCarX;
		carY = newCarY;
	
	}
	
	private void dashboard() {
	
		System.out.println("Location: (" + carX + "," + carY + ")");
		System.out.println("Facing: " + carDir);
		System.out.println("Gear: " + carGear);
	
	}
	
	static boolean isInteger(String str, int baseTen) {
	
		Scanner input = new Scanner(str.trim());
		if(!input.hasNextInt(baseTen)) return false;
		input.nextInt(baseTen);
		return !input.hasNext();
	
	}

}


class World {

	int sizeX 			= 100;
	int sizeY 			= 100;
	int obstacleCount 	= 5;
	
	World() {
	
		
	
	}
	
	public int getSizeX() {
	
		return sizeX;
	
	}
	
	public int getSizeY() {
	
		return sizeY;
	
	}
	
	public int getObstacleCount() {
	
		return obstacleCount;
	
	}
	
	

}


class Obstacle {

	int centroidX;
	int centroidY;
	int width;
	int height;
	int wBound;
	int eBound;
	int nBound;
	int sBound;
	String name;
	boolean found = false;
	
	Obstacle(String nameP, World world) {
	
		Random random = new Random();
		
		width = random.nextInt(5 + 1);
		height = random.nextInt(5 + 1);
		
		if (width % 2 == 0 ) {
		
			width++;
			
		}
		
		if (height % 2 == 0) {
		
			height++;
			
		}
		
		int bufferX = (width - 1)/2;
		int bufferY = (height - 1)/2;
		
		centroidX = random.nextInt(world.getSizeX() + 1 - bufferX);
		centroidY = random.nextInt(world.getSizeY() + 1 - bufferX);
		
		name = "obstacle" + nameP;
		
		wBound = centroidX - bufferX;
		eBound = centroidX + bufferX;
		nBound = centroidY - bufferY;
		sBound = centroidY + bufferY;
		
	
	}
	
	Obstacle(int nBound, int sBound, int eBound, int wBound) {
	
		width = eBound - wBound;
		height = sBound - nBound;
	
		centroidX = (width + 1)/2;
		centroidY = (height + 1)/2;
		
		name = world;
		found = true;
	
	}
	
	public int getCentroidX() {
	
		return centroidX;
	
	}
	
	public int getCentroidY() {
	
		return centroidY;
	
	}
	
	public int getWidth() {
	
		return width;
	
	}
	
	public int getHeight() {
	
		return height;
	
	}
	
	public String getName() {
	
		return name;
	
	}
	
	public void setName(String nm) {
	
		name = nm;
	
	}
	
	public void setFound() {
	
		found = true;
	
	}
	
	public int getWBound() {
	
		return wBound;
	
	}
	
	public int getEBound() {
	
		return eBound;
	
	}
	
	public int getNBound() {
	
		return nBound;
	
	}
	
	public int getUBound() {
	
		return uBound;
	
	}

}
