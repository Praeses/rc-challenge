package com.praeses.interviews.rcchallenge;

import java.io.*;
import java.util.*;
import java.lang.System.*;
import java.lang.Integer;
import java.lang.Math;

public class RC_Challenge {

    public static void main(String[] args) {

        RC_Challenge rc = new RC_Challenge();

    }

    RC_Challenge() {

		World world = new World();

    }

    class World {

		private int sizeX 					= 50;
		private int sizeY 					= 50;
		private int obstacleCount			= 5;
		private Spot[][] spotGrid 			= new Spot[sizeX][sizeY];
		private List<Obstacle> obstacles 	= new ArrayList<Obstacle>();
		public Car car;
		private Interface ui;

		World() {

			for (int xSpot = 0; xSpot < getWorldSizeX(); xSpot++) {

				for (int ySpot = 0; ySpot < getWorldSizeY(); ySpot++) {

					spotGrid[xSpot][ySpot] = new Spot(xSpot,ySpot);

				}
			}

			for(int obstIndex = 0; obstIndex < getObstacleCount(); obstIndex++) {

				getObstacleList().add(new Obstacle(obstIndex));

			}

			this.car = new Car(findCarStartXPosition(), 0);

		 	this.ui = new Interface();

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

		public Car getCar() {

			return this.car;

		}

		public Interface getUI() {

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

		public class Interface {

			Interface() {

				printConsoleUI();
				getUserInput();

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

							car.move(true);
							break;

						case "a":

							car.setHeadingSelector("left");
							break;

						case "s":

							car.move(false);
							break;

						case "d":

							car.setHeadingSelector("right");
							break;

						case "exit":

							System.exit(0);

					}

					printConsoleUI();

				}
			}

			public void printConsoleUI() {

				clearConsole();

				System.out.println("Car's Position is:  ("
					+ getCar().getXCoor() + " , "
					+ getCar().getYCoor() + ")");
				System.out.println("The car is currently facing: "
					+ getCar().getHeadingString());
				System.out.println("Is the car blocked? "
					+ getCar().getIsBlocked());
				System.out.println("The blocking obstacle is: "
					+getCar().getBlockedName());

				System.out.println();

				System.out.println("w - forward, s - back, a - left, d - right");
				System.out.println("exit - quit the program");

			}

			public void clearConsole() {

				System.out.print("\033[H\033[2J");
    			System.out.flush();

			}

		}

        public class Car {

			private int xCoor;
			private int yCoor;
			private int[][] heading 	= {{0,-1},{1,0},{0,1},{-1,0}};
			private int headingSelector = 0;
			private Boolean isBlocked 	= false;
			private String blockedName	= "";

			Car(int xCoor, int yCoor) {

				setXCoor(xCoor);
				setYCoor(yCoor);

				System.out.println("" + getXCoor());
				System.out.println("" + getYCoor());

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

				if(getSpot(nextSpotX, nextSpotY).isObstacle()) {

					setIsBlocked(true);
					setBlockedName(getSpot(nextSpotX,
						nextSpotY).getObstacleName());
					return false;

				}

				setXCoor(nextSpotX);
				setYCoor(nextSpotY);
				setIsBlocked(false);
				setBlockedName("");
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

			public String getBlockedName() {

				return this.blockedName;

			}

			public void setBlockedName(String blockedName) {

				this.blockedName = blockedName;

			}
        }

        public class Obstacle {

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
				printObstInfo();
				tagSpotsAsObstacle();

    	    }

			private void printObstInfo() {

				System.out.println(getName());
				System.out.println("nbound - " + getNBound());
				System.out.println("sbound - " + getSBound());
				System.out.println("ebound - " + getEBound());
				System.out.println("wbound - " + getWBound());
				System.out.println("xCentroid - " + getCentroidX());
				System.out.println("yCentroid - " + getCentroidY());
				System.out.println();

			}

			private void tagSpotsAsObstacle() {

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

        	private void initializeDimensions() {

            	Random random = new Random();
            	int width;
            	int height;

    			width = random.nextInt(5);
    	    	height = random.nextInt(5);

    			if (width % 2 == 0 ) {

    				width++;

    			}

    			if (height % 2 == 0) {

    				height++;

    			}

            	setWidth(width);
            	setHeight(height);

        	}

        	private void initializeLocation() {

            	Random random = new Random();

            	int bufferX = (getWidth() - 1)/2;//buffer is radius minus the centroid.
    			int bufferY = (getHeight() - 1)/2;

    			int centroidX = random.nextInt(getWorldSizeX() - bufferX - 1);
    			int centroidY = random.nextInt(getWorldSizeY() - bufferY - 1);

    			if (centroidX < 4) {

    				centroidX = 4;

    			}

    			if (centroidY < 4) {

    				centroidY = 4;

    			}

				setCentroidX(centroidX);
				setCentroidY(centroidY);

				initializeBounds(bufferX, bufferY);

        	}

			private void initializeBounds(int bufferX, int bufferY) {

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

			public void setCentroidX(int centroidX) {

				this.centroidX = centroidX;

			}

			public void setCentroidY(int centroidY) {

				this.centroidY = centroidY;

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

        	public void setNBound(int nBound) {

            	this.nBound = nBound;

        	}

        	public void setSBound(int sBound) {

            	this.sBound = sBound;

        	}

        	public void setEBound(int eBound) {

            	this.eBound = eBound;

        	}

        	public void setWBound(int wBound) {

            	this.wBound = wBound;

        	}

    	}

    	public class Spot {

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

    			return this.obstacle;

    		}

    		public int getObstacleIndex() {

    			return this.obstIndex;

    		}

    		public String getObstacleName() {

    			return this.obstName;

    		}

    		public void setObstacle(boolean obstacle) {

    			this.obstacle = obstacle;

    		}

    		public void setObstacleIndex(int index) {

    			this.obstIndex = index;

    		}

    		public void setObstacleName(String name) {

    			this.obstName = name;

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
