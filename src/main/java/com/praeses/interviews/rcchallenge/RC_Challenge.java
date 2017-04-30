package com.praeses.interviews.rcchallenge;

/** RC_Challenge is a skill demonstration project. It's a simple game like
car program that simulates a car on a 2D grid with obstacles. The standard
controls used are W,A,S,D just like most PC games involving movement. However,
after each input "enter" must be pressed. The grid size is 50x50 (x,y) and has
5 randomly placed/sized obstacles. **/

import java.io.*;
import java.util.*;
import java.lang.System.*;
import java.lang.Integer;
import java.lang.Math;

/** RC_Challenge is the outer class that holds the main() method and creates
the World object. **/
public class RC_Challenge {

    public static void main(String[] args) {

        RC_Challenge rc = new RC_Challenge();

    }

    RC_Challenge() {

		World world = new World();

    }

    /** World is the controller class. It creates and holds all object
    references. Additionally, it defines some basic characteristics of the world
    like the size of the x,y plane and the number of obstacles. **/
    class World {

		private int sizeX 					= 50;
		private int sizeY 					= 50;
		private int obstacleCount			= 5;
		private Spot[][] spotGrid 			= new Spot[sizeX][sizeY];
		private List<Obstacle> obstacles 	= new ArrayList<Obstacle>();
		public Car car;
		private Interface ui;

		World() {

		 	spotCreate();
            obstacleCreate();
            carCreate();
            interfaceCreate();

		}

        private void spotCreate() {

            for (int xSpot = 0; xSpot < getWorldSizeX(); xSpot++) {

				for (int ySpot = 0; ySpot < getWorldSizeY(); ySpot++) {

					spotGrid[xSpot][ySpot] = new Spot(xSpot,ySpot);

				}
			}
        }

        private void obstacleCreate() {

            for(int obstIndex = 0; obstIndex < getObstacleCount(); obstIndex++) {

				getObstacleList().add(new Obstacle(obstIndex));

			}

        }

        private void carCreate() {

            this.car = new Car(findCarStartXPosition(), 0);

        }

        private void interfaceCreate() {

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

        //Finds a non-obstacle occupied position for the car to start at.
		public int findCarStartXPosition() {

			int xPos = 0;
			int yPos = 0;

			while(getSpot(xPos, yPos).isObstacle()) {

				xPos++;

			}

			return xPos;

		}

        /** Interface class handles all user interaction. **/
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

				Boolean quit = false;

				while(!quit) {

					switch(userInputString()) {

						case "w":

							car.move(true);
							break;

						case "a":

							car.turn("left");
							break;

						case "s":

							car.move(false);
							break;

						case "d":

							car.turn("right");
							break;

                        case "hunt":

                            car.findAnObstacle();
                            break;

						case "exit":

							quit = true;

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
                System.out.println("hunt - auto-pilot to find an obstacle");
				System.out.println("exit - quit the program");

			}

			public void clearConsole() {

				System.out.print("\033[H\033[2J");
    			System.out.flush();

			}

		}

        /** Car class is the car being manipulated by the user. The heading or
        direction the car is facing is represented by a 2D array and a selector
        value for 1 dimension of that array. The first dimension of the heading
        array is 4 addresses in size and each address represents a direction
        (north, south, east, west). As the car is turned left and right, the
        selector value is shifted up or down by 1, thus selecting the new
        heading. The 2nd dimension that describes the heading selected is just
        a pair of values (x,y) or (north/south,east/west). One value will be 0
        and the other either 1 or -1. The upper left (northwest) corner is the
        (0,0) position on the grid. So, if the selector value is 0, north is
        selected. North's value pair is {0,-1} which means it will move 0
        spaces in the X axis and move -1 in the y axis. **/
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

			}

            /** Adds or subtracts the heading value pair depending on if the
            user moved the car forward or back. Then checks if the movement is
            out of bonds and wraps the value if so. Then checks if the movement
            is blocked by an obstacle and sets the isBlocked flag and blocking
            obstacle name if so. Finally, if there is no blocking obstacle, the
            car's position is updated and the isBlocked flag/blocking
            obstacle name are reset to default values. **/
			public void move(Boolean forward) {

				int nextSpotX;
				int nextSpotY;

				if(forward) {

					nextSpotX = getXCoor() + getXHeading();
					nextSpotY = getYCoor() + getYHeading();

				} else {

					nextSpotX = getXCoor() - getXHeading();
					nextSpotY = getYCoor() - getYHeading();

				}

				nextSpotX = xAxisEdgeWrapping(nextSpotX);
				nextSpotY = yAxisEdgeWrapping(nextSpotY);

				if(getSpot(nextSpotX, nextSpotY).isObstacle()) {

					setIsBlocked(true);
					setBlockedName(getSpot(nextSpotX,
						nextSpotY).getObstacleName());
                    return;

				}

				setXCoor(nextSpotX);
				setYCoor(nextSpotY);
				setIsBlocked(false);
				setBlockedName("");

			}

            private int xAxisEdgeWrapping(int nextSpotX) {

                if(nextSpotX >= getWorldSizeX()) {

					nextSpotX = 0;

				} else if(nextSpotX < 0) {

					nextSpotX = getWorldSizeX() - 1;

				}

                return nextSpotX;

            }

            private int yAxisEdgeWrapping(int nextSpotY) {

                if(nextSpotY >= getWorldSizeY()) {

					nextSpotY = 0;

				} else if(nextSpotY < 0) {

					nextSpotY = getWorldSizeY() - 1;

				}

                return nextSpotY;

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

            /** The car's heading selector value is -- or ++ depending on if
            the user requested a left or right turn. Right turns are ++ and
            left turns are --. Obviously the selector value must be kept in
            range of the array. **/
			public void turn(String leftOrRight) {

				if(leftOrRight.equals("right")) {

					if(getHeadingSelector() >= 3) {

						setHeadingSelector(0);

					} else {

						setHeadingSelector(getHeadingSelector() + 1);

					}
				} else if(leftOrRight.equals("left")) {

					if(getHeadingSelector() <= 0) {

						setHeadingSelector(3);

					} else {

						setHeadingSelector(getHeadingSelector() - 1);

					}
				}
			}

            public void setHeadingSelector(int heading) {

                if(heading >= 0 && heading <= 3) {

                    this.headingSelector = heading;

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

            public void findAnObstacle() {

                int numberConsecutiveMoves = 0;

                setHeadingSelector(1);

                while(!getIsBlocked()) {

                    move(true);
                    numberConsecutiveMoves++;

                    if(numberConsecutiveMoves >= getWorldSizeX()) {

                        turn("right");
                        move(true);
                        turn("left");
                        numberConsecutiveMoves = 0;

                    }
                }
            }
        }

        /** Obstacle class represents the obstacles on the grid. They are
        always an odd width and height. This enables simple location of the
        obstacles via their centroid. With dimensions and a centroid, the
        bounds of the obstacle are defined. Finally, all the Spots within the
        area of the obstacle are tagged as belonging to that obstacle.
        Additionally, each obstacle is named and has an index value for use
        with its reference array in the World class. There is care given so
        that obstacles do not overlap the edge of the grid. So they are always
        placed sufficiently far away from the edge relative to their max
        size. **/
        public class Obstacle {

            private int maxWidth = 5;
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
				tagSpotsAsObstacle();

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

    			width = random.nextInt(getMaxWidth());
    	    	height = random.nextInt(getMaxWidth());

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

    			if (centroidX < bufferX + 1) {

    				centroidX = bufferX + 1;

    			}

    			if (centroidY < bufferY + 1) {

    				centroidY = bufferY + 1;

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

            private int getMaxWidth() {

                return this.maxWidth;

            }

    	}

        /** Spot class represents a specific point on the grid. So, each point
        on the World's x,y grid is defined by an object with attributes.
        Primarily, this enables the easy detection of obstacles. Any Spot that
        is the location of an Obstacle is flagged as such enabling the Car to
        check each Spot for Obstacles. **/
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
