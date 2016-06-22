package com.praeses.interviews.rcchallenge;

import java.io.*;
import java.util.*;
import java.lang.System.*;
import java.lang.Integer;

public class Main {

    public static void main(String[] args) {
		
		World world = new World();
		
		ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
		for (int i = 0; i < world.getObstacleCount(); i++) {
		
			obstacles.add(new Obstacle(Integer.toString(i), world));
		
		}
		
		Car rcCar = new Car();
		
    	}
}

class Car {

	public int carX 		= 0;
	public int carY 		= 0;
	public String carDir 	= "north";
	int carGear 			= 1;
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

	Car() {
		
		ignition();
		driverSeat();
		
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
	
	private void driverSeat() {
	
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
			
			if (arrayLength < 2) {
			
				
			
			}
		
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
	
	}
	
	private void transmission(int shifter) {
	
		carGear = shifter;
	
	}
	
	private void gasPedal(int distance) {
	
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

}
