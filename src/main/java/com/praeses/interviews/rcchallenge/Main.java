package com.praeses.interviews.rcchallenge;

import java.io.*;
import java.util.*;
import java.lang.System.*;
import java.lang.Integer;
import java.lang.Math;


/**rc-challenge is a game like program that simulates an "rc car". The listeners
should handle most incorrect user inputs. However, they are picky and require
exact formatting. The program is written with a future jFrame animation of the
car, world, and obstacles in mind.
*/


//Main class is just a convenient place for the main method.
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

/**Car class uses many directly access/edited public variables. Also, this is
where the input listeners live (ignition() and driverSeat() methods)
*/
class Car {

	public int carX 		= 0;
	public int carY 		= 0;
	public String carDir 	= "north";
	int carGear 			= 1;
	public String heading	= "north";
	public boolean blocked	= false;
	String startXYString		= "Please enter the starting coordinate 'x,y':";
	String startXYError 	= "Must be integers and formatted 'x,y'!";
	String startDirString 	= "Please input the starting direction example-'west':";
	String startGearString 	= "Please input the starting gear (-1 or 1):";
	String intError 		= "Please enter an integer!";
	String dirError 		= "Must be north, south, east, or west!";
	String gearError 		= "Must be -1 or 1";
	String badCom 			= "Unrecognized Command. Type 'help' for guidance.";
	static Scanner scInput;

	Car(World world, ArrayList<Obstacle> obstacles) {
		
		ignition(world);
		Animate draw = new Animate();
		driverSeat(world, obstacles);
		
	}
	
/**ignition() is where the initial values (x and y coordinates, direction, and
gear) for the car instance are read from the user.*/
	private void ignition(World world) {
		
		boolean gettingUserInput = true;
		boolean checkingInput = true;
		int arrayLength = 0;
		String userInput = "init";
		setHeading();
		
		scInput = new Scanner(System.in);
		
		while (checkingInput == true) {
			while (gettingUserInput == true) {
				try {
					System.out.println(startXYString);
					userInput = scInput.next();
					gettingUserInput = false;
   				} catch (Exception e) {
   						System.out.println(startXYError);
   						scInput.nextLine();
   				}
   			}
   			gettingUserInput = true;
   		
   			List<String> userInputList = Arrays.asList(userInput.split("\\s*,\\s*"));		
			arrayLength = userInputList.size();
		
			if (arrayLength == 2 && isInteger(userInputList.get(0)) && isInteger(userInputList.get(1))) {
		
				carX = Integer.parseInt(userInputList.get(0));
				carY = Integer.parseInt(userInputList.get(1));
				
				if (carX > world.getSizeX() || carY > world.getSizeY()) {
				
					System.out.println("Out of bounds! The world is " + world.getSizeX() + "x" + world.getSizeY() + ".");
				
				} else if (world.getSpot(carX, carY).isObstacle() == true) {
				
					System.out.println("Obstacle! Try a different spot!");
				
				} else {
				
					checkingInput = false;
				
				}
		
			} else {
			
				System.out.println(startXYError);
			
			}
		
		}
		checkingInput = true;
   		
   		while (gettingUserInput == true) {
   			try {
				System.out.println(startDirString);
				carDir = scInput.next();
				switch (carDir) {
					case "north": case "south": case "east": case "west":
						setHeading();
						gettingUserInput = false;
						break;
					default:
						System.out.println(dirError);
				}
			} catch (Exception e) {
   				System.out.println(dirError);
   				scInput.nextLine();
   			}
   		};
   		gettingUserInput = true;
   		
   		while (gettingUserInput == true) {
   			try {
				System.out.println(startGearString);
				carGear = scInput.nextInt();
				switch (carGear) {
					case -1: case 1: 
						setHeading();
						gettingUserInput = false;
						break;
					default:
						System.out.println(gearError);
				}
			} catch (Exception e) {
   				System.out.println(gearError);
   				scInput.nextLine();
   			}
   		}

	}

/**driverSeat() is where the bulk of user interaction occurs. After the
initial values are read in ignition(), all other user inputs occur here. All the
user inputs read here are passed to the neccessary methods to be acted on.
*/
	private void driverSeat(World world, ArrayList<Obstacle> obstacles) {
	
		boolean gettingUserInput = true;
		boolean checkingInput = true;	
		String drivingGuide = "Example commands: 'west,-1,1', '50', 'gear,1', 'exit', 'help' ";
		String delimit = ",";
		String userInput = "init";	
		int arrayLength;
		int temp;
		
		while (checkingInput == true) {
			while (gettingUserInput == true) {
				try {
					System.out.println("Drive!");
					System.out.println(drivingGuide);
					userInput = scInput.next();
					scInput.nextLine();
					gettingUserInput = false;
				} catch (Exception e) {
					System.out.println("error");
					scInput.nextLine();
				}
				
			}
			gettingUserInput = true;
			
			List<String> userInputList = Arrays.asList(userInput.split("\\s*,\\s*"));		
			arrayLength = userInputList.size();		
		
			if (arrayLength == 3) {				
				
				if (isInteger(userInputList.get(1)) == true && isInteger(userInputList.get(2)) == true) {
				
					steeringWheel(userInputList.get(0));
					transmission(Integer.parseInt(userInputList.get(1)));
					gasPedal(Integer.parseInt(userInputList.get(2)), world);
					
				} else {
				
					System.out.println(badCom);
				
				}
				
			} else if (arrayLength == 2) {
			
				if (userInputList.get(0).equals("gear") == true && isInteger(userInputList.get(1))) {
				
					transmission(Integer.parseInt(userInputList.get(1)));
					
				} else if (userInputList.get(0).equals("obstacle") == true && isInteger(userInputList.get(1))){
				
					//goToObstacle(Integer.parseInt(userInputList.get(1)));
				
				} else {
				
					System.out.println(badCom);
				
				}
				
			} else if (arrayLength == 1) {
			
				if (userInputList.get(0).equals("north") == true || userInputList.get(0).equals("south") == true || userInputList.get(0).equals("east") == true || userInputList.get(0).equals("west") == true) {
				
					steeringWheel(userInputList.get(0));
					
				} else if (isInteger(userInputList.get(0)) == true) {
				
					gasPedal(Integer.parseInt(userInputList.get(0)), world);
					
				} else if (userInputList.get(0).equals("help") == true) {
				
					System.out.println(drivingGuide);
					
				} else if (userInputList.get(0).equals("exit") == true) {
				
					checkingInput = false;
					
				} else if (userInputList.get(0).equals("around the block") == true) {
				
				} else if (userInputList.get(0).equals("find obstacle") == true) {
			
				} else {
				
					System.out.println(badCom);
				
				}
				
			} else {
			
				System.out.println(badCom);
			
			}
			
			dashboard();
			
		}
		
	}

//All methods were named to match their function relative to actual car parts.
//Obviously, steeringWheel() changes where the car is facing. Most all the names
//should be obvious.
	private void steeringWheel(String dir) {
	
		carDir = dir;
		setHeading();
	
	}
	
	private void transmission(int shifter) {
	
		carGear = shifter;
		setHeading();
	
	}
	
//heading is the combination of direction and gear (-1,1 aka reverse,forward).
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
	
/**gasPedal() also contains object detection and edge wrapping since these
functions are so closely tied to the iterative movement of the car.
*/
	private void gasPedal(int distance, World world) {
	
		int nextSpot = 0;
	
		switch (heading) {
			case "north":
			
				for (int i = 1; i <= distance; i++) {
				
					nextSpot = carY - 1;
			
					if (nextSpot < 1) {
						
					nextSpot = world.getSizeY();
						
					}
				
					if (world.getSpot(carX, nextSpot).isObstacle() == true) {
					
						blocked = true;
						System.out.println("Found " + world.getSpot(carX, nextSpot).getObstacleName() + ". Path is blocked");
						break;
					
					} else {
						
						carY = nextSpot;
					
					}
				
				}
				break;
				
			case "south":
			
				for (int i = 1; i <= distance; i++) {
				
					nextSpot = carY + 1;
			
					if (nextSpot > world.getSizeY()) {
						
						nextSpot = 1;
						
					}
				
					if (world.getSpot(carX, nextSpot).isObstacle() == true) {
					
						blocked = true;
						System.out.println("Found " + world.getSpot(carX, nextSpot).getObstacleName() + ". Path is blocked");
						break;
					
					} else {
						
							carY = nextSpot;
					
					}
				
				}
				break;
				
			case "east":
				
				for (int i = 1; i <= distance; i++) {
				
					nextSpot = carX + 1;
			
					if (nextSpot > world.getSizeX()) {
						
						nextSpot = 1;
						
					}
				
					if (world.getSpot(nextSpot, carY).isObstacle() == true) {
					
						blocked = true;
						System.out.println("Found " + world.getSpot(nextSpot, carY).getObstacleName() + ". Path is blocked");
						break;
					
					} else {
						
							carX = nextSpot;
					
					}
				
				}
				break;
				
			case "west":
				
				for (int i = 1; i <= distance; i++) {
				
					nextSpot = carX - 1;
			
					if (nextSpot < 1) {
						
						nextSpot = world.getSizeX();
						
					}
				
					if (world.getSpot(nextSpot, carY).isObstacle() == true) {
					
						blocked = true;
						System.out.println("Found " + world.getSpot(nextSpot, carY).getObstacleName() + ". Path is blocked");
						break;
					
					} else {
						
							carX = nextSpot;
					
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
	
	static boolean isInteger(String str) {
	
		Scanner input = new Scanner(str.trim());
		if(!input.hasNextInt(10)) return false;
		input.nextInt(10);
		return !input.hasNext();
	
	}

}


class World {

	int sizeX 			= 100;
	int sizeY 			= 100;
	int obstacleCount 	= 5;
	
	Spot[][] grid;
	
	World() {
	
		//the 0 indeces of grid are empty. This is so that the actual grid
		//location matches the array location.
		grid = new Spot[sizeX+1][sizeY+1]; 
		
		for (int i = 1; i <= sizeX; i++) {
		
			for (int j = 1; j <= sizeY; j++) {
			
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

/**Obstacles must have odd dimensions. That is so they have an integer centroid.
This enables future functionality for the car to "go to" an obstacle and be
able to drive towards the centroid. Also, this allows a simple way to designate
the location of an obstacle. Instead of relying on occupied space alone.
*/
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
		
		int bufferX = (width - 1)/2;//buffer is radius minus the centroid.
		int bufferY = (height - 1)/2;
		
		centroidX = random.nextInt(world.getSizeX() - bufferX - 1);
		centroidY = random.nextInt(world.getSizeY() - bufferX - 1);
		
		if (centroidX < 4) {
		
			centroidX = 4;
		
		}
		
		if (centroidY < 4) {
		
			centroidY = 4;
		
		}
		
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

/**Spot class allows very easy obstacle detection. That is the primary reason
that the grid is made up of an object instead of just tracking with integers.
*/
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
