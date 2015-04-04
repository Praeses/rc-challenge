package main.java.com.praeses.interviews.rcchallenge;

/*******************
 * @author Taylor
 * @version 1.0
 *
 *This class is used in Main.java to aid in an RC Car simulation.
 */

public class Car {
	
	/******************
	 * INSTANCE VARIABLES
	 */
	
	private String orientation;		//North, South, East, or West
	private int xcord;				//x-coordinate
	private int ycord;				//y-coordinate
	
	/******************
	 * CONSTRUCTOR
	 * @param o - String representing the orientation of the car.
	 * @param x - String representing the x coordinate of the car.
	 * @param y - String representing the y coordinate of the car.
	 */
	public Car(String o, int x, int y){
		orientation = o;
		xcord = x;
		ycord = y;
	}
	
	/*******************
	 * GETS & SETS
	 */
	public String getOrientation(){
		return orientation;
	}
	
	public int getX(){
		return xcord;
	}
	
	public int getY(){
		return ycord;
	}
	
	/********************
	 * This method determines which character should represent the RC Car based on orientation.
	 * @return dirChar - String representing the direction the car is facing.
	 */
	
	public String getDirection(){
		String direction = "";
		switch(orientation){
			case "N": direction = "^";
						break;
			case "W": direction = "<";
						break;
			case "S": direction = "V";
						break;
			case "E": direction = ">";
						break;
		}
		
		return direction;
	}
	
	public void setOrient(String o){
		orientation = o;
	}
	
	public void setX(int x){
		xcord = x;
	}
	
	public void setY(int y){
		ycord = y;
	}
	
	/***********************
	 * OTHER METHODS
	 */
	
	/************************
	 * This method performs the operations to turn the car to the left.
	 * @return orientation - String containing the cardinal direction the car is facing.
	 */
	public String turnLeft(){
		switch(orientation){
			case "N": orientation = "W";
						break;
			case "W": orientation = "S";
						break;
			case "S": orientation = "E";
						break;
			case "E": orientation = "N";
						break;
		}
		
		return orientation;
	}
	
	
	/************************
	 * This method performs the operations to turn the car to the right.
	 * @return orientation - String containing the cardinal direction the car is facing.
	 */
	public String turnRight(){
		switch(orientation){
			case "N": orientation = "E";
					break;
			case "W": orientation = "N";
					break;
			case "S": orientation = "W";
					break;
			case "E": orientation = "S";
					break;
		}
		
		return orientation;
	}
	
	/*************
	 * This method performs the operations needed to move the car forward.
	 * @param x - Integer that contains the max x-value of the grid.
	 * @param y - Integer that contains the max y-value of the grid.
	 */
	
	public void moveForward(int x, int y){
		switch(orientation){	
			case "N": ycord -= 1;
							if(ycord < 0) 
								ycord = y + ycord;
					break;
			case "W": xcord -= 1;
							if(xcord < 0) 
								xcord = x + xcord;
					break;
			case "S": ycord += 1;
							if(ycord >= y) 
								ycord = ycord - y;
					break;
			case "E": xcord += 1;
							if(xcord >= x) 
								xcord =  xcord - x;
					break;
		}
	}
	
	
	/*************
	 * This method performs the operations needed to move the car backward.
	 * @param x - Integer that contains the max x-value of the grid.
	 * @param y - Integer that contains the max y-value of the grid.
	 */
	public void moveBackward(int x, int y){
		switch(orientation){	
			case "N": ycord += 1;
							if(ycord >= y) 
								ycord -= y;
					break;
			case "W": xcord += 1;
							if(xcord >= x) 
								xcord -= x;
					break;
			case "S": ycord -= 1;
							if(ycord < 0) 
								ycord += y;
					break;
			case "E": xcord -= 1;
							if(xcord < 0) 
								xcord +=  x;
					break;
		}
	}
	
	/*****************
	 * TOSTRING
	 */
	
	public String toString(){
		return "Orientation: " + orientation + 
					"\nCoordinates: (" + xcord +", " + ycord +")";
	}
}
	
	

