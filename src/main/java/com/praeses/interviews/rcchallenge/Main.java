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
		
		for (int i = 0; i < world.getObstacleCount(); i++) {
		
			obstacles.add(new Obstacle(i, world));
		
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
	public boolean blocked	= false;
	int[] gearBox			= {-1,1};
	String startXYString		= "Please enter the starting coordinate 'x,y':";
	String startXYError 	= "Must be integers and formatted 'x,y'!";
	String startDirString 	= "Please input the starting direction:";
	String startGearString 	= "Please input the starting gear:";
	String intError 		= "Please enter an integer!";
	String dirError 		= "Must be north, south, east, or west!";
	String gearError 		= "Must be -1 or 1";
	String badCom 			= "Unrecognized Command. Type 'help' for guidance.";
	static Scanner scInput;

	Car(World world, ArrayList<Obstacle> obstacles) {
		
		ignition(world);
		driverSeat(world, obstacles);
		
	}
	
	private void ignition(World world) {
		
		boolean loop = true;
		boolean loopB = true;
		int arrayLength = 0;
		String userInput = "init";
		setHeading();
		
		scInput = new Scanner(System.in);
		
		do {
			do {
				try {
					System.out.println(startXYString);
					userInput = scInput.next();
					loop = false;
   				} catch (Exception e) {
   						System.out.println(startXYError);
   						scInput.nextLine();
   				}
   			} while (loop == true);
   			loop = true;
   		
   			List<String> userInputList = Arrays.asList(userInput.split("\\s*,\\s*"));		
			arrayLength = userInputList.size();
		
			if (arrayLength == 2 && isInteger(userInputList.get(0),10) && isInteger(userInputList.get(1),10)) {
		
				carX = Integer.parseInt(userInputList.get(0));
				carY = Integer.parseInt(userInputList.get(1));
				
				if (world.getSpot(carX, carY).isObstacle() == true) {
				
					System.out.println("Obstacle! Try a different spot!");
				
				} else {
				
					loopB = false;
				
				}
		
			} else {
			
				System.out.println(startXYError);
			
			}
		
		} while (loopB == true);
		loopB = true;
   		
   		do {
   			try {
				System.out.println(startDirString);
				carDir = scInput.next();
				switch (carDir) {
					case "north": case "south": case "east": case "west":
						setHeading();
						loop = false;
						break;
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
						setHeading();
						loop = false;
						break;
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
		
			if (arrayLength == 3) {				
				
				if (isInteger(userInputList.get(1), 10) == true && isInteger(userInputList.get(2), 10) == true) {
				
					steeringWheel(userInputList.get(0));
					transmission(Integer.parseInt(userInputList.get(1)));
					gasPedal(Integer.parseInt(userInputList.get(2)), world);
					
				} else {
				
					System.out.println(badCom);
				
				}
				
			} else if (arrayLength == 2) {
			
				if (userInputList.get(0).equals("gear") == true && isInteger(userInputList.get(1),10)) {
				
					transmission(Integer.parseInt(userInputList.get(1)));
					
				} else if (userInputList.get(0).equals("obstacle") == true && isInteger(userInputList.get(1),10)){
				
					//goToObstacle(Integer.parseInt(userInputList.get(1)));
				
				} else {
				
					System.out.println(badCom);
				
				}
				
			} else if (arrayLength == 1) {
			
				if (userInputList.get(0).equals("north") == true || userInputList.get(0).equals("south") == true || userInputList.get(0).equals("east") == true || userInputList.get(0).equals("west") == true) {
				
					steeringWheel(userInputList.get(0));
					
				} else if (isInteger(userInputList.get(0), 10) == true) {
				
					gasPedal(Integer.parseInt(userInputList.get(0)), world);
					
				} else if (userInputList.get(0).equals("help") == true) {
				
					System.out.println(drivingGuide);
					
				} else if (userInputList.get(0).equals("exit") == true) {
				
					loopB = false;
					
				} else if (userInputList.get(0).equals("around the block") == true) {
				
				} else if (userInputList.get(0).equals("find obstacle") == true) {
			
				} else {
				
					System.out.println(badCom);
				
				}
				
			} else {
			
				System.out.println(badCom);
			
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
	
	private void gasPedal(int distance, World world) {
	
		switch (heading) {
			case "north":
			
				for (int i = 1; i <= distance; i++) {
				
					if (world.getSpot(carY - 1, carX).isObstacle() == true) {
					
						blocked = true;
						System.out.println("Found " + world.getSpot(carY - 1, carX).getObstacleName() + ". Path is blocked");
						break;
					
					} else {
					
						carY--;
					
					}
				
				}
				break;
				
			case "south":
			
				for (int i = 1; i <= distance; i++) {
				
					if (world.getSpot(carY + 1, carX).isObstacle() == true) {
					
						blocked = true;
						System.out.println("Found " + world.getSpot(carY + 1, carX).getObstacleName() + ". Path is blocked");
						break;
					
					} else {
					
						carY++;
					
					}
				
				}
				break;
				
			case "east":
				
				for (int i = 1; i <= distance; i++) {
				
					if (world.getSpot(carY, carX + 1).isObstacle() == true) {
					
						blocked = true;
						System.out.println("Found " + world.getSpot(carY, carX + 1).getObstacleName() + ". Path is blocked");
						break;
					
					} else {
					
						carX++;
					
					}
				
				}
				break;
				
			case "west":
				
				for (int i = 1; i <= distance; i++) {
				
					if (world.getSpot(carY, carX - 1).isObstacle() == true) {
					
						blocked = true;
						System.out.println("Found " + world.getSpot(carY, carX - 1).getObstacleName() + ". Path is blocked");
						break;
					
					} else {
					
						carX--;
					
					}
				
				}
				break;
				
			default:
			
				System.out.println(dirError);
		}
	
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
	
	Spot[][] grid;
	
	World() {
	
		grid = new Spot[sizeX][sizeY];
		
		for (int i = 1; i < sizeX; i++) {
		
			for (int j = 1; j < sizeY; j++) {
			
				grid[i][j] = new Spot(i,j);
				
			}
			
		}
	
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
	
	public Spot getSpot(int x, int y) {
	
		return grid[x][y];
	
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
	int index;
	String name;
	boolean found = false;
	int foundX;
	int foundY;
	
	Obstacle(int ind, World world) {
	
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
		
		centroidX = random.nextInt(world.getSizeX() - bufferX);
		centroidY = random.nextInt(world.getSizeY() - bufferX);
		
		name = "obstacle" + Integer.toString(ind);
		
		wBound = centroidX - bufferX;
		eBound = centroidX + bufferX;
		nBound = centroidY - bufferY;
		sBound = centroidY + bufferY;
		
		for (int i = wBound; i <= eBound; i++) {
		
			for (int j = nBound; j <= sBound; j++) {
		
				world.getSpot(i,j).setObstacle(true);
				world.getSpot(i,j).setObstacleName(name);
				world.getSpot(i,j).setObstacleIndex(ind);
			
			}
		
		}	
	
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
	
	public int getSBound() {
	
		return sBound;
	
	}

}

class Spot {

	boolean obst;
	String obstName;
	int obstIndex;
	int xAddress;
	int yAddress;

	Spot (int x, int y) {
	
		obst = false;
		obstName = "";
		obstIndex = 0;
		xAddress = x;
		yAddress = y;
	
	}
	
	public boolean isObstacle() {
	
		return obst;
	
	}
	
	public int getObstacleIndex() {
	
		return obstIndex;
	
	}
	
	public String getObstacleName() {
	
		return obstName;
	
	}
	
	public void setObstacle(boolean ob) {
	
		obst = ob;
	
	}
	
	public void setObstacleIndex(int index) {
	
		obstIndex = index;
	
	}
	
	public void setObstacleName(String name) {
	
		obstName = name;
	
	}

}
