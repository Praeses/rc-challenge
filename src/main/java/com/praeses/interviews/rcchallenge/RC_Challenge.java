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

    class Car {



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

    	Obstacle(int ind, World world) {

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

        private initializeDimensions() {

            Random random = new Random();
            int width;
            int height;

    		width = random.nextInt(5 + 1);
    	    height = random.nextInt(5 + 1);

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

    }

    class World {



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
