package main.java.com.praeses.interviews.rcchallenge;

/*************************************************
 * @author Taylor Stutts
 * @version 1.0
 * 
 * This program uses command line arguments to create a Remote Control
 * 	Car Simulation. In order to achieve the most reuse of code, the main
 * 	method is broken up over several different methods to allow ease of
 * 	reading and changing.
 * 
 * Dependencies:
 * 	The program depends on the Car class.
 * 
 */

import java.io.*;
import java.util.*;

public class Main {
	
	/**********************
	 * These are our instance variables. These are referenced
	 * 	directly by all the included methods in the Main class.
	 */
	
	private static Car playerCar;
	private static String response = "";
	private static String[] coords;
	private static Scanner scan = new Scanner(System.in);
	private static String[][] grid;
	private static String orientation;
	private static int maxCoord;
	private static int xcoord;
	private static int ycoord;
	
	/**************
	 * This method allows easier printing of the RC Grid.
	 */
	
	public static void printGrid(){
		for(int i = 0; i < grid.length; i++){
			System.out.println(Arrays.toString(grid[i]));
		}
		System.out.println("\n");
	}
	
	/****************
	 * This method checks if a cell of the RC Grid is empty.
	 * @param x - Integer that gives the x coordinate of the cell to be checked.
	 * @param y - Integer that gives the y coordinate of the cell to be checked.
	 * @return isEmpty - Boolean that tells if the cell is empty.
	 */
	
	public static boolean gridSlotEmpty(int x, int y){
		String value = grid[y-1][x-1];
		return value.equals("_");
	}
	
	/******************
	 * This method creates a grid based on the starting coordinates of the car.
	 * 	The created grid will always be square in nature and will never be smaller than
	 *  5x5. The creation uses the largest coordinate to create a grid.
	 */
	
	public static void createAGrid(){
		
		if(Integer.parseInt(coords[0]) < Integer.parseInt(coords[1]))
			maxCoord = ycoord;
		else
			maxCoord = xcoord;
		
		if(maxCoord < 5)
			maxCoord = 5;
		else
			maxCoord = maxCoord * 2;
		
		grid = new String[maxCoord][maxCoord];
		//Fill the array with underscore characters.
		for(int i = 0; i < grid.length; i++){
			Arrays.fill(grid[i], "_");
		}
	}
	
	
	/******************************
	 * This method provides the interface for adding obstacles to a
	 * 	generated grid.
	 */
	
	public static void createObstacles(){
		//Determine if the user wants to set obstacles manually. This is currently the only working option.
		do{
			System.out.println("Do you want to manually create obstacles? ['y' or 'n'<-DOES NOT WORK YET]");
			response = scan.nextLine().toUpperCase();
		}while(!response.equals("Y") && !response.equals("N"));
		
		if(response.equals("Y")){
			
			//Allow the user to enter coordinates.
			//	If a coordinate pair is not valid, the program will let the user know and ask again.
			//	The user may enter f to finish.
			do{
				System.out.println("Please input a valid coordinate for the next obstacle.[Your car: " + (playerCar.getX() + 1) + "," + (playerCar.getY() + 1) + ". Type f to Finish.]");
				response = scan.nextLine().toUpperCase();
				
				if(response.matches("[0-9][0-9]*,[0-9][0-9]*")){
					String[] strArray = response.split(",");
					int x = Integer.parseInt(strArray[0]);
					int y = Integer.parseInt(strArray[1]);
					if(x <= grid[0].length && y <= grid.length){
						if(gridSlotEmpty(x, y))
							grid[y-1][x-1] = "O";
						else
							System.out.println("Something is blocking the way! Try another coordinate.");
					}else
						System.out.println("Those coordinates are not valid.");
					printGrid();
				}else if(!response.equals("F")){
					System.out.println("Those coordinates are not valid. Please type f to finish.");
					printGrid();
				}
				
			}while(!response.equals("F"));
		
		//This was the beginning of the automated obstacle additions.
		}else{
			do{
				System.out.println("How many obstacles should be added? ['r' for random]");
				response = scan.nextLine().toUpperCase();
				
			}while(!response.matches("[0-9]*") && !response.equals("R"));
		}
	}
	
	
	
	/*********************
	 * This method makes a move on the RCGrid, then tests for validity of the move.
	 * 	If the move is invalid, it is handled by testMove().
	 * @return isValid - Boolean representing whether the current move is valid.
	 */
	public static boolean validMovement(){
		switch(response){
		case "L": playerCar.turnLeft();
						break;
		case "R": playerCar.turnRight();
						break;
		case "F": grid[playerCar.getY()][playerCar.getX()] = "_";
						playerCar.moveForward(grid[0].length, grid.length);
						break;
		case "B": grid[playerCar.getY()][playerCar.getX()] = "_";
						playerCar.moveBackward(grid[0].length, grid.length);
						break;
		}
		
		return testMove();
	}
	
	
	
	/*******************
	 * This method makes multiple moves on the RCGrid via a string of commands.
	 * 	While some code is repeated, it does use validMovement() to perform some
	 * 	actions.
	 */
	
	public static void validMovementString(){
		String[] sample = response.split(",");
		for(int i = 0; i < sample.length; i++){
			switch(sample[i]){
				case "L": playerCar.turnLeft();
						  grid[playerCar.getY()][playerCar.getX()] = playerCar.getDirection();
						  break;
				case "R": playerCar.turnRight();
						  grid[playerCar.getY()][playerCar.getX()] = playerCar.getDirection();
						  break;
				case "F": response = "F";
						  validMovement();
						  grid[playerCar.getY()][playerCar.getX()] = playerCar.getDirection();
						  break;
				case "B": response = "B";
						  validMovement();
						  grid[playerCar.getY()][playerCar.getX()] = playerCar.getDirection();
						  break;
				default:  if(sample[i].matches("[RLFB]X[0-9][0-9]*")){
							String[] moveDesc = sample[i].split("X");
							response = moveDesc[0];
							int j = 0;
							boolean keepMoving = true;
							while(j < Integer.parseInt(moveDesc[1]) && keepMoving){
								if(validMovement()){
									grid[playerCar.getY()][playerCar.getX()] = playerCar.getDirection();
									printGrid();
								}
								else{
									grid[playerCar.getY()][playerCar.getX()] = playerCar.getDirection();
									keepMoving = false;
								}
								j++;
							}
						  }else{
							System.out.println("Command number " + (i+1) + " was invalid. Stopping execution.");
							grid[playerCar.getY()][playerCar.getX()] = playerCar.getDirection();
						  }
			}
		}
	}
	
	
	
	/**************
	 * This method tests the validity of a move. If the move was invalid, then invalidMovement() is called.
	 * 	
	 * @return isValid - Boolean representing whether the current move is valid.
	 */
	public static boolean testMove(){
		boolean flag = false;
		if((response.equals("F") || response.equals("B")) && grid[playerCar.getY()][playerCar.getX()] != "_"){
			invalidMovement();
			flag = false;
		}else
			flag = true;
		return flag;
	}
	
	
	/************
	 * This method reverses invalid moves and alerts the user the path is blocked.
	 */
	
	public static void invalidMovement(){
		System.out.println("You cannot go that way.");
		if (response.equals("F"))
			playerCar.moveBackward(grid[0].length, grid.length);
		else
			playerCar.moveForward(grid[0].length, grid.length);
	}
	
	
	
	
	/******************
	 * This method simulates an interactive mode for the user. It allows the user to continuously
	 * 	enter commands to move the RC Car. Once the user enters c, the program is finished.
	 */
	public static void interactiveMode(){
		
		//Until the user types C for close, continue asking for movements.
				do{
					System.out.println("\nInput your next direction. [l = left, r = right, f=forward, b=backword, c=close.");
					System.out.println("                             Input may be single letter or string formatted like so:");
					System.out.println("                               l,r,f,b,lx#,rx#,fx#,bx# E.x. l,fx2,rx2,b]");
					response = scan.nextLine().toUpperCase();
					if(response.length() > 1){
						validMovementString();
					}else{
						validMovement();
						grid[playerCar.getY()][playerCar.getX()] = playerCar.getDirection();
					}
					
					System.out.println("Current State: ");
					printGrid();
					
				}while(!response.equals("C"));
				scan.close();
		
	}
	
	
	/***********************
	 * This method contains the interface options for the user to create a manual session.
	 * 	This is currently the only form of session.
	 */
	
	
    public static void manualSetup(){
    	
    	//This method is called from Main if the user want's to manually control the RC Car.
    	//
    	//We must get the beginning parameters from the user.
		//
		//First we get the beginning X and Y coordinates.
		//	These coordinates assume the user would start counting at 1. Not 0.
		do{
    		System.out.println("Please input the x and y coordinates for your RC Car.");
    		System.out.println("   (In the form 'x,y' w/ NO spaces.)");
    		response = scan.nextLine().toUpperCase();
		}while(!response.matches("[0-9]*,[0-9]*"));
		
		//Split the coordinates into an array of strings.
		//We then save the coordinates as integers.
		coords = response.split(",");
		xcoord = Integer.parseInt(coords[0]) - 1;
		ycoord = Integer.parseInt(coords[1]) - 1;
		
		//Next we get the orientation of the car.
		//	It can be facing North, South, East, or West.
		do{
    		System.out.println("Please input the orientation for your RC Car.");
    		System.out.println("   ('N', 'S', 'E', 'W')");
    		response = scan.nextLine().toUpperCase();
		}while(!response.matches("[NWSE]"));
		
		//Save the orientation.
		orientation = response;
		
		createAGrid();
		
		//Create the new player Car object.
		playerCar = new Car(orientation, xcoord, ycoord);
		
		//Plug the character representing the car into the grid.
		grid[ycoord][xcoord] = playerCar.getDirection();
		
		createObstacles();
		
		//Print out the grid for the user to view.
		
		printGrid();
		
		interactiveMode();
    	
    }
    
    
    
    
    /*******************
     * This is our main method. Everything starts here.
     * 
     * @param args
     */
	
	public static void main(String[] args) {
    	
        
    	//Determine whether the user would like to play with an RC car manually
    	//	or follow a predefined route.
    	
    	do{
	        System.out.println("How would you like to control the RC Car?");
	        System.out.println("   (By File: 'f'<-DOES NOT WORK YET; Manual Control: 'm')");
	        response = scan.nextLine().toUpperCase();
    	}while(!response.equals("F") && !response.equals("M"));
    	
    	
    	//If the user selected manual mode...
    	if(response.equals("M")){
    		
    		manualSetup();
    		
    	}else if(response.equals("F")){
    		
    	}
        
    }
}
