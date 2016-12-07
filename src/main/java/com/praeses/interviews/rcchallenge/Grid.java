package com.praeses.interviews.rcchallenge;


import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Grid extends JPanel {// this is the constructor for road object int the main method and is the panael whre most of the action happens
	static final int width=500,height=500; 
	public int move=0;// this is used to stop the car just doing a one 180 with out moving 
	public int stringBreak=0;// once again this is used to see if the string command stops mid way thought because of a collison between the car and a block
	private boolean obstaclesOn =false; 
	private Car theCar = new Car(0,0,20,"S");// the car object is created here 
	private obstacle block; 
	private ArrayList<obstacle> randomCorse= new ArrayList<obstacle>(); // much like in when running from a string of commands this array list holds all of the obstacles
    public Grid(){
    	setPreferredSize(new Dimension(width,height));
    }
    public void paint(Graphics g){// this is the paint method that is called once when program starts and recalled when the image needs to be updated
    	int x=0;
    	int y=0;
    	g.clearRect(0, 0, width, height);// this line clears the road object so there are no leftover images from past car or obstacles. 
        for (x=0;x<width/20;x++){
    		for (y=0;y<height/20;y++){
    		    g.drawRect(x*20, y*20, 20, 20);// this for loop makes the grid 
    		}
    	}
        theCar.drawCar(g);// simply puts the car on the grid
        if (obstaclesOn= true) {// the obstacles are optional so they are not redrawn unless they are in use 
        	int i;
        	for (i=0;i<randomCorse.size();i++){// loop that paints all of the randomly generated obstacles 
        		randomCorse.get(i).drawBlock(g);// drawing said obstacles
        	}
        }
    }
    public void carFoward(){// this method is called when the car needs to move forward
    	String carfacing = new String(theCar.getfacing());// this method works by looking at the cars current facing 
    	int y=this.theCar.gety();// then increasing the x or y values of the car but before committing the changes it will check a head to see if there is a block in the way 
    	int x=this.theCar.getx();
    	this.move=0;
    	this.stringBreak=0;
		switch (carfacing ){
		case "S":// set up for when the car is facing south 
			y++;
				if (y>24)
					y=0;
			this.theCar.sety(y);
				if (checkLegalMove()== false){
					this.stringBreak=1;// this value is used to break out of the string command 
					y--;
						if (y<0)// this check here is what causes the wrapping from one side of the grid to the other
							y=24;
					this.theCar.sety(y);
				}
			repaint();
		    break;
		case "N":// slightly different then for south 
			y--;
				if (y<0)
					y=24;
			this.theCar.sety(y);
			if (checkLegalMove()== false){
				this.stringBreak=1;
				y++;
					if (y>24)
						y=0;
				this.theCar.sety(y);
			}
			repaint();
		    break;
		case "W": // The set up for moving forward when facing west
			x--;
				if (x<0)
					x=24;
			this.theCar.setx(x);
			if (checkLegalMove()== false){
				this.stringBreak=1;
				x++;
					if (x>24)
						x=0;
				this.theCar.setx(x);
			}
			repaint();
		    break;
		case "E": // the set up for moving forward when facing west 
			x++;
			if (x>24)
				x=0;
			this.theCar.setx(x);
			if (checkLegalMove()== false){
				this.stringBreak=1;
				x--;
					if (x<0)
						x=24;
				this.theCar.setx(x);
			}
			repaint();
		    break;
		}
    }
	public void carBack(){// same as the method that was made for moving forward but is inversed 
	    	String carfacing = theCar.getfacing();
	    	int y=this.theCar.gety();
	    	int x=this.theCar.getx();
	    	this.move=0;
	    	this.stringBreak=0;
			switch (carfacing ){
			case "S": 
				y--;
				if (y<0)
					y=24;
				this.theCar.sety(y);
				if (checkLegalMove()== false){
					this.stringBreak=1;
					y++;
						if (y>24)
							y=0;
					this.theCar.sety(y);
				}
				repaint();
			    break;
			case "N": 
				y++;
				if(y>24)
					y=0;
				this.theCar.sety(y);
				if (checkLegalMove()== false){
					this.stringBreak=1;
					y--;
						if (y<0)
							y=24;
					this.theCar.sety(y);
				}
				repaint();
			    break;
			case "W": 
				x++;
				if(x>24)
					x=0;
				this.theCar.setx(x);
				if (checkLegalMove()== false){
					this.stringBreak=1;
					x--;
						if (x<0)
							x=24;
					this.theCar.setx(x);
				}
				repaint();
			    break;
			case "E": 
				x--;
				if(x<0)
					x=24;
				this.theCar.setx(x);
				if (checkLegalMove()== false){
					this.stringBreak=1;
					x++;
						if (x>24)
							x=0;
					this.theCar.setx(x);
				}
				repaint();
			    break;
			}		
    }
    public void carLeft(){// this method checked the facing of the car and then changed it to the facing to the left
          String carfacing = theCar.getfacing();
          if (this.move ==0){
        	  this.move=1;// the move is changed here so as to not let the car turn 180 degrees with out moving at least on space 
        	  switch (carfacing){
        	  	case ("N"):
        	  		theCar.setfacing("W");
        	  		break; 
        	  	case ("S"):
        	  		theCar.setfacing("E");
        	  		break; 
        	  	case ("E"):
        	  		theCar.setfacing("N");
        	  		break; 
        	  	case ("W"):
        	  		theCar.setfacing("S");
          	  		break; 
          	}
          }
          else 
        	  JOptionPane.showMessageDialog( Grid.this, String.format("Can not turn twice in a row"));
    }
    public void carRight(){// same as carLeft but to the right instead 
        String carfacing = theCar.getfacing(); 
        if (this.move ==0){
        	this.move=1; 
        	switch (carfacing){
        		case ("N"):
      	  			theCar.setfacing("E");
        	  		break; 
        		case ("S"):
      	  			theCar.setfacing("W");
        	  		break; 
        		case ("E"):
      	  			theCar.setfacing("S");
        	  		break; 
        		case ("W"):
      	  			theCar.setfacing("N");
        	  		break; 
        	}
        }
        else 
      	  JOptionPane.showMessageDialog( Grid.this, String.format("Can not turn twice in a row"));
    }
    public void generateObstacles(){// this method makes random values for the x an y values of the obstacles 
    	 this.obstaclesOn=true;// in this case i am only doing 100 but the button could be pressed for more of them 
    	 int i=0;
    	 for (i=0;i<100;i++){
    		 int ranX= (int) (Math.random()*25);
    		 int ranY= (int) (Math.random()*25);
    		 if (ranX==theCar.getx()){
    			 if (ranY==theCar.gety()){// this if statement is to stop the obstacles from spawning on the car
    				 i--; 
    				 continue;
    			 }
    		 }
         block=new obstacle(ranX,ranY,20);
         randomCorse.add(block);// the new block added to the array list 
    	 }
    	 repaint();// called so that the new obstacles can be added to the grid 
    }
    public void destroyObstacles(){// this methods clears all of the blocks objects made and then repaints the screen
    	randomCorse.clear();
    	this.obstaclesOn=false; 
    	repaint(); 
    }
    public boolean checkLegalMove(){// this method compares the next move of the car with all of the generated blocks 
    	boolean legal=true;// if even one matches the loop is broken and the car is reverted to right before it made that move
    	int i; 
    	for (i=0;i<randomCorse.size();i++){
    		if (randomCorse.get(i).getx()==theCar.getx() && randomCorse.get(i).gety()==theCar.gety()){
    				legal=false; 
    				JOptionPane.showMessageDialog( Grid.this, String.format("The car can not go here turn back or pick a different starting palce"));
    				break;
    		}
    	}
		return legal; 
    }
    public void initalCar(int x,int y,String facing){// as the name states this sets the starting point of the car
    	theCar.setx(x);// the values passed to this are generated from user input into text fields then passed here to be set 
    	theCar.sety(y);
    	theCar.setfacing(facing);
    		if (checkLegalMove()==false){
    		    theCar.setx(0);
    		    theCar.sety(0);
    		    theCar.setfacing("N");
    		}
    	repaint();// after the car has changed its values its is redrawn at its new location 
    }
	public String facingLabel(){// this method allows the main class to have access to the facing through this method 
    	return theCar.getfacing();// main can access the grid and the grid has access to the car 
    }
    
  }

