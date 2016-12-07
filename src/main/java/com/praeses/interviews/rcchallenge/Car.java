package com.praeses.interviews.rcchallenge;


import java.awt.Color;
import java.awt.Graphics;

public class Car {// this is the constructor of the car object 
      private int xCor,yCor,height,width;
      private String facing= new String(); 
      public Car (int xcor,int ycor,int carsize, String initalFacing){
    	  this.xCor=xcor;
    	  this.yCor=ycor;
    	  this.height=carsize;
    	  this.width=carsize;
    	  this.facing=initalFacing; // all of the needed values are setup here 
      }
      public void drawCar(Graphics g){// this method holds the information that is called upon by the grid object to 
    	  g.setColor(Color.GREEN);//draw the car on the grid 
    	  g.fillRect(xCor*width, yCor*height, width, height);
      }
      public int getx(){// the rest are just simple sets and gets for the main factors of the car 
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
      public void setfacing(String newFacing){
    	  this.facing=newFacing;
      }
      public String getfacing (){
    	  return this.facing;
      }
}
