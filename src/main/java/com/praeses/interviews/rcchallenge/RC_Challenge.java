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



    }

    class World {

        class Car {

			private int xCoor;
			private int yCoor;
			private String heading;
			private Boolean isForward;
			private Boolean isBlocked;

			Car(int xCoor, int yCoor, String heading, Boolean isForward,
				Boolean isBlocked) {

				setXCoor(xCoor);
				setYCoor(yCoor);
				setHeading(heading);
				setIsForward(isForward);
				setIsBlocked(isBlocked);

			}

			public void move(int distance) {

				int nextSpot;

				switch (getHeading()) {

					case "north"

				}

			}

			public void moveNorth(int distance) {

				for(int position = 1; position <= distance; position++) {

					

				}

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

			public void setHeading(String heading) {

				this.xCoor = xCoor;

			}

			public String getHeading()) {

				return this.heading;

			}

			public void setIsForward(Boolean isForward) {

				this.isForward = isForward;

			}

			public Boolean getIsForward() {

				return this.isForward;

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

    			int centroidX = random.nextInt(world.getSizeX() - bufferX - 1);
    			int centroidY = random.nextInt(world.getSizeY() - bufferX - 1);

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
