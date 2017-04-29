package com.praeses.interviews.rcchallenge;

import java.io.*;
import java.util.*;
import java.lang.System.*;
import java.lang.Integer;
import java.lang.Math;

public class RC_Challenge {

    public static void main(String[] args) {

        RC-Challenge rc = new RC-Challenge();

    }

    RC_Challenge() {

		World world = new World();

    }

    class World {

		int sizeX 					= 100;
		int sizeY 					= 100;
		int numberOfObstacles		= 5;
		Spot[][] spotGrid 			= new Spot[sizeX][sizeY];
		List<Obstacle> obstacles 	= new ArrayList<Obstacle>();
		Car car;
		Interface ui				= new Interface();

		World() {

			for (int xSpot = 0; xSpot < getSizeX(); xSpot++) {

				for (int ySpot = 0; ySpot < getSizeY(); ySpot++) {

					getSpot(xSpot,ySpot) = new Spot(xSpot,ySpot);

				}
			}

			for(int obstIndex = 0; obstIndex < getObstacleCount(); obstIndex++) {

				getObstacleList().add(new Obstacle(obstIndex));

			}

			getCar() = new Car(findCarStartXPosition(), 0);



		}

		public Spot getSpot(int xCoor, int yCoor) {

			return this.spotGrid[xCoor][yCoor];

		}

		public Spot[][] getSpotArray() {

			return this.spotGrid;

		}

		public List<Obstacle> getObstacleList() {

			return this.obstacles;

		}

		public getCar() {

			return this.car;

		}

		public getUI() {

			return this.ui;

		}

		public int getWorldSizeX() {

			return this.sizeX;

		}

		public int getWorldSizeY() {

			return this.sizeY;

		}

		public int getObstacleCount() {

			return this.obstacleCount;

		}

		public int findCarStartXPosition() {

			int xPos = 0;
			int yPos = 0;

			while(getSpot(xPos, yPos).isObstacle()) {

				xPos++;

			}

			return xPos;

		}

		class Interface() {

			Interface() {

				printConsoleUI();

			}

			public String userInputString() {

	            Scanner scanner = new Scanner(System.in);
	            return scanner.next();

	        }

			public void getUserInput() {

				Boolean validInput = false;

				while(!validInput) {

					switch(userInputString()) {

						case "w":

							getCar().move(true);

						case "a":

							getCar().setHeadingSelector("left");

						case "s":

							getCar().move(false);

						case "d":

							getCar().setHeadingSelector("right");

						case "exit":

							System.exit(0);

					}
				}
			}

			public void printConsoleUI() {

				clearConsole();

				System.out.println("Car's Position is:  ("
					+ getCar().getXCoor().toString() + " , "
					+ getCar().getYCoor().toString() + ")");
				System.out.println("The car is currently facing: "
					+ getCar().getHeadingString());
				System.out.println("Is the car blocked? "
					+ getCar().getIsBlocked().toString());

				System.out.println();

				System.out.println("w - forward, s - back, a - left, d - right");
				System.out.println("exit - quit the program");

			}

			public void clearConsole() {

				System.out.print("\033[H\033[2J");
    			System.out.flush();

			}

		}

        class Car {

			private int xCoor;
			private int yCoor;
			private int[][] heading 	= {0,-1},{1,0},{0,1},{-1,0};
			private int headingSelector = 0;
			private Boolean isBlocked 	= false;

			Car(int xCoor, int yCoor) {

				setXCoor(xCoor);
				setYCoor(yCoor);

			}

			public Boolean move(Boolean forward) {

				int nextSpotX;
				int nextSpotY;

				if(forward) {

					nextSpotX = getXCoor() + getXHeading();
					nextSpotY = getYCoor() + getYHeading();

				} else {

					nextSpotX = getXCoor() - getXHeading();
					nextSpotY = getYCoor() - getYHeading();

				}

				if(getSpot(nextSpotX, nextSpotY).isObstacle()) {

					setIsBlocked(true);
					return false;

				}

				if(nextSpotX >= getWorldSizeX()) {

					nextSpotX = 0;

				} else if(nextSpotX < 0) {

					nextSpotX = getWorldSizeX() - 1;

				}

				if(nextSpotY >= getWorldSizeY()) {

					nextSpotY = 0;

				} else if(nextSpotY < 0) {

					nextSpotY = getWorldSizeY() - 1;

				}

				setXCoor(nextSpotX);
				setYCoor(nextSpotY);
				setIsBlocked(false);
				return true;

			}

			public void setXCoor(int xCoor) {

				this.xCoor = xCoor;

			}

			public int getXCoor() {

				return this.xCoor;

			}

			public void setYCoor(int yCoor) {

				this.yCoor = yCoor;

			}

			public int getYCoor() {

				return this.yCoor;

			}

			public void setHeadingSelector(String leftOrRight) {

				if(leftOrRight.equals("right")) {

					if(this.headingSelector >= 3) {

						this.headingSelector = 0;

					} else {

						this.headingSelector++;

					}
				} else if(leftOrRight.equals("left")) {

					if(this.headingSelector <= 0) {

						this.headingSelector = 3;

					} else {

						this.headingSelector--;

					}
				}
			}

			public int getXHeading() {

				return this.heading[getHeadingSelector()][0];

			}

			public int getYHeading() {

				return this.heading[getHeadingSelector()][1];

			}

			public int getHeadingSelector() {

				return this.headingSelector;

			}

			public String getHeadingString() {

				String heading;

				switch(getHeadingSelector()) {

					case 0:

						return "north";

					case 1:

						return "east";

					case 2:

						return "south";

					case 3:

						return "west";

				}

				return "error";

			}

			public void setIsBlocked(Boolean isBlocked) {

				this.isBlocked = isBlocked;

			}

			public Boolean getIsBlocked() {

				return this.isBlocked;

			}
        }

        class Obstacle {

            private int centroidX;
    	    private int centroidY;
    	    private int width;
    	    private int height;
    	    private int wBound;
    	    private int eBound;
    	    private int nBound;
    	    private int sBound;
    	    private int index;
    	    private String name;
    	    private boolean found = false;
    	    private int foundX;
    	    private int foundY;

    	    Obstacle(int index) {

				setName("obstacle" + Integer.toString(index));
				setIndex(index);
    			initializeDimensions();
				initializeLocation();


    	    }

			private tagSpotsAsObstacle() {

				for (int spotXCoor = getWBound(); spotXCoor <= getEBound();
					spotXCoor++) {

    				for (int spotYCoor = getNBound(); spotYCoor <= getSBound();
						spotYCoor++) {

    				    	getSpot(spotXCoor,spotYCoor).setObstacle(true);
    				    	getSpot(spotXCoor,spotYCoor).
								setObstacleName(getName());
    				    	getSpot(spotXCoor,spotYCoor).
								setObstacleIndex(getIndex());

    			    }
    	        }
			}

        	private initializeDimensions() {

            	Random random = new Random();
            	int width;
            	int height;

    			width = random.nextInt(6);
    	    	height = random.nextInt(6);

    			if (width % 2 == 0 ) {

    				width++;

    			}

    			if (height % 2 == 0) {

    				height++;

    			}

            	setWidth(width);
            	setHeight(height);

        	}

        	private initializeLocation() {

            	Random random = new Random();

            	int bufferX = (getWidth() - 1)/2;//buffer is radius minus the centroid.
    			int bufferY = (getHeight() - 1)/2;

    			int centroidX = random.nextInt(getWorldSizeX() - bufferX - 1);
    			int centroidY = random.nextInt(getWorldSizeY() - bufferX - 1);

    			if (centroidX < 4) {

    				centroidX = 4;

    			}

    			if (centroidY < 4) {

    				centroidY = 4;

    			}

				initializeBounds(bufferX, bufferY);

        	}

			private initializeBounds(int bufferX, int bufferY) {

				setWBound(getCentroidX() - bufferX);
				setEBound(getCentroidX() + bufferX);
				setNBound(getCentroidY() - bufferY);
				setSBound(getCentroidY() + bufferY);

			}

    		public int getCentroidX() {

    			return this.centroidX;

    		}

    		public int getCentroidY() {

    			return this.centroidY;

    		}

    		public int getWidth() {

    			return this.width;

    		}

    		public int getHeight() {

    			return this.height;

    		}

    		public String getName() {

    			return this.name;

    		}

    		public void setName(String name) {

    			this.name = name;

    		}

			public int getIndex() {

				return this.index;

			}

			public void setIndex(int index) {

				this.index = index;

			}

    		public void setFound() {

    			this.found = true;

    		}

    		public int getWBound() {

    			return this.wBound;

    		}

    		public int getEBound() {

    			return this.eBound;

    		}

    		public int getNBound() {

    			return this.nBound;

    		}

    		public int getSBound() {

    			return this.sBound;

    		}

        	public void setHeight(int height) {

            	this.height = height;

        	}

        	public void setWidth(int width) {

            	this.width = width;

        	}

        	public void setNBound(nBound) {

            	this.nBound = nBound;

        	}

        	public void setSBound(sBound) {

            	this.sBound = sBound;

        	}

        	public void setEBound(eBound) {

            	this.eBound = eBound;

        	}

        	public void setWBound(wBound) {

            	this.wBound = wBound;

        	}

    	}

    	class Spot {

        	private boolean obstacle;
    		private String obstName;
    		private int obstIndex;
    		private int xAddress;
    		private int yAddress;

    		Spot (int x, int y) {

    			setObstacle(false);
    			setObstacleName("");
    			setObstacleIndex(0);
    			setXAddress(x);
    			setYAddress(y);

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

    		public void setObstacle(boolean obstacle) {

    			this.obstacle = obstacle;

    		}

    		public void setObstacleIndex(int index) {

    			obstIndex = index;

    		}

    		public void setObstacleName(String name) {

    			obstName = name;

    		}

        	public void setXAddress(int x) {

            	this.xAddress = x;

        	}

        	public void setYAddress(int y) {

            	this.yAddress = y;

        	}

        	public int getXAddress() {

            	return this.xAddress;

        	}

        	public int getYAddress() {

            	return this.yAddress;

        	}
		}
    }
}
