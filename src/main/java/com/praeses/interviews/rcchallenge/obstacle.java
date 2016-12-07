package com.praeses.interviews.rcchallenge;


import java.awt.Color;
import java.awt.Graphics;

public class obstacle {// this is the constructor of the block objects that are made in the road object 
	private int xCor,yCor,height,width; 
    public obstacle (int xcor,int ycor,int opsize){
  	  this.xCor=xcor;
  	  this.yCor=ycor;
  	  this.height=opsize;
  	  this.width=opsize; // just like the car the main factors of an obstacle are started here 
    }
    public void drawBlock(Graphics g){// this method holds the information need to draw an obstacle object its just 
  	  g.setColor(Color.BLACK);// a car with out the facing but a different color 
  	  g.fillRect(xCor*width, yCor*height, width, height);
    }
    public int getx(){// the rest of the methods are just the set and gets for the x and y values of all the obstacles 
  	  return this.xCor; 
    }
    public int gety(){
  	  return this.yCor; 
    }
    public void sety(int ycor){
  	  this.yCor=ycor; 
    }
    public void setx(int xcor){
  	  this.xCor=xcor; 
    }
}
