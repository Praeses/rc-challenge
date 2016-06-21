package com.praeses.interviews.rcchallenge;

import java.io.*;
import java.util.*;
import java.lang.System.*;

public class Main {

    public static void main(String[] args) {
		
		Car rcCar = new Car();
		
    	}
}

class Car {

	public int carX = 0;
	public int carY = 0;
	public String carDir = "north";
	public String startXString = "Please input the starting X Coordinate:";
	public String startYString = "Please input the starting Y Coordinate:";
	public String startDirString = "Please input the starting direction:";
	public String intError = "Please enter an integer!";
	public String dirError = "Must be north, south, east, or west!";
	public Scanner userInput;

	Car() {
		
		//userInput = new Scanner(System.in);
		
		carStart();
		
	}
	
	private void carStart() {
		
		private boolean loop = true;
		
		userInput = new Scanner(System.in);
		
		do {
			try {
				System.out.print(startXString);
				carX = userInput.nextInt();
				loop = false;
   			} catch (Exception e) {
   					System.out.print(intError);
   					userInput.nextLine();
   			}
   		} while (loop == true);
   		loop = true;
   		
   		do {
			try {
				System.out.print(startYString);
				carY = userInput.nextInt();
				loop = false;
   			} catch (Exception e) {
   					System.out.print(intError);
   					userInput.nextLine();
   			}
   		} while (loop == true);
   		loop = true;
   		
   		do {
			try {
				System.out.print(startDirString);
				carDir = userInput.next();
				switch (carDir) {
					case "north": case "south": case "east": case "west": 
					loop = false;
				}
   			} catch (Exception e) {
   					System.out.print(dirError);
   					userInput.nextLine();
   			}
   		} while (loop == true);
   		
   		userInput.close();

	}
	
	private void pilotSeat() {
	
		private boolean loop = true;	
		private String drivingGuide = "TBD";
		private String scInput;
	
		userInput = new Scanner(System.in);
		
		while (scInput != "exit") {
		
			do {
				try {
					System.out.print(drivingGuide);
					scInput = userInput.next();
					switch (scInput) {
						case "north": case "south": case "east": case "west": 
						steeringWheel(scInput);
						loop = false;
						case "help":
						System.out.print(drivingGuide);
						loop = false;
					}
   				} catch (Exception e) {
   					System.out.print("Error");
   					userInput.nextLine();
   				}
   			} while (loop == true);
   			loop = true;
   		
   		}
	
	}
	
	private void steeringWheel(String dir) {
	
		carDir = dir;
	
	}

}
