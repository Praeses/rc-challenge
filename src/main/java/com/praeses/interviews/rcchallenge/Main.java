package com.praeses.interviews.rcchallenge;

import java.io.*;
import java.util.*;
import java.lang.System.*;
import java.lang.Integer;

public class Main {

    public static void main(String[] args) {
		
		Car rcCar = new Car();
		
    	}
}

class Car {

	public int carX = 0;
	public int carY = 0;
	public String carDir = "north";
	int carGear = 1;
	int[] gearBox = {-1,1};
	String startXString = "Please input the starting X Coordinate:";
	String startYString = "Please input the starting Y Coordinate:";
	String startDirString = "Please input the starting direction:";
	String intError = "Please enter an integer!";
	String dirError = "Must be north, south, east, or west!";
	String badCom = "Unrecognized Command. Type 'help' for guidance.";
	Scanner scInput;

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
			}
			} catch (Exception e) {
   				System.out.println(dirError);
   				scInput.nextLine();
   			}
   		} while (loop == true);
   		
   		scInput.close();

	}
	
	private void driverSeat() {
	
		boolean loop = true;
		boolean loopB = true;	
		String drivingGuide = "TBD";
		String delimit = "[,]";
		String userInput = "init";
		String[] userInputArray;	
		int arrayLength;
		int temp;
		
		scInput = new Scanner(System.in);
		
		do {
			do {
				try {
				System.out.println("Drive!");
				userInput = scInput.nextLine();
				System.out.println("test");
				loop = false;
				} catch (Exception e) {
				System.out.print("error");
				scInput.nextLine();
				}
				
			} while (loop == true);
			
			userInputArray = userInput.split(delimit);		
			arrayLength = userInputArray.length;
		
			for (int i = 0; i < arrayLength; i++) {
				userInputArray[i] = userInputArray[i].trim();
			}
		
		
			if (arrayLength == 3) {				
				
				if (isInteger(userInputArray[1], 10) == true && isInteger(userInputArray[2], 10) == true) {
				
					steeringWheel(userInputArray[0]);
					transmission(Integer.parseInt(userInputArray[1]));
					gasPedal(Integer.parseInt(userInputArray[2]));
					
				} else {
				
					System.out.println(badCom);
				
				}
				
			} else if (arrayLength == 2) {
			
				if (userInputArray[0] == "gear" && isInteger(userInputArray[1],10)) {
				
					transmission(Integer.parseInt(userInputArray[1]));
					
				} else {
				
					System.out.println(badCom);
				
				}
				
			} else if (arrayLength == 1) {
			
				if (userInputArray[0] == "north" || userInputArray[0] == "south" || userInputArray[0] == "east" || userInputArray[0] == "west") {
				
					steeringWheel(userInputArray[0]);
					
				} else if (isInteger(userInputArray[0], 10) == true) {
				
					gasPedal(Integer.parseInt(userInputArray[0]));
					
				} else if (userInputArray[0] == "help") {
				
					System.out.print(drivingGuide);
					
				} else if (userInputArray[0] == "exit") {
				
					loopB = false;
					
				} else if (userInputArray[0] == "around the block") {
				
				} else if (userInputArray[0] == "find obstacle") {
			
				} else {
				
					System.out.println(badCom);
				
				}
				
			} else {
			
				System.out.println(badCom);
			
			}
			
		} while (loopB == true);
		
		scInput.close();
	
	}
	
	private void steeringWheel(String dir) {
	
		carDir = dir;
		
		System.out.println("Car is now facing " + carDir + ".");
	
	}
	
	private void transmission(int shifter) {
	
	carGear = shifter;
	
	System.out.println("Car is now in gear " + carGear + ".");
	
	}
	
	private void gasPedal(int distance) {
	
		int newCarX = 0;
		int newCarY = 0;
	
		if (carDir == "north") {
		
			if (carGear > 0) {
				newCarY = carY - distance;
			} else if (carGear < 0) {
				newCarY = carY + distance;
			}
		
		} else if (carDir == "south") {
		
			if (carGear > 0) {
				newCarY = carY + distance;
			} else if (carGear <0) {
				newCarY = carY - distance;
			}
		
		} else if (carDir == "east") {
		
			if (carGear > 0) {
				newCarX = carY + distance;
			} else if (carGear < 0) {
				newCarX = carY - distance;
			}
		
		} else if (carDir == "west") {
		
			if (carGear > 0) {
				newCarX = carY - distance;
			} else if (carGear < 0) {
				newCarX = carY + distance;
			}
		
		}
		
		carX = newCarX;
		carY = newCarY;
		
		System.out.println("Car location is (" + carX + "," + carY + ").");
	
	}
	
	static boolean isInteger(String str, int baseTen) {
	
		Scanner input = new Scanner(str.trim());
		if(!input.hasNextInt(baseTen)) return false;
		input.nextInt(baseTen);
		return !input.hasNext();
	
	}

}
