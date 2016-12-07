package com.praeses.interviews.rcchallenge;


import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/* 
 * Rc car Challenge
 * By Zachery Lange
 * for Praeses interview homework
 * 
 * this program use the main class as the frame with the grid object acting as a panel to be put into the main frame
 * the other two objects are drawn on the panel that is in the main frame
 * the car class being the rc car and the obstacle class making the walls for the rc car avoid
 */
public class Main extends JFrame {
	private JButton b1,b2,b3,b4,b5,b6,b7,b8;
	private JTextField tfix,tfiy,tfif,tfStringCommand;
	private Grid road=new Grid(); // this is the panel where most of the action takes place 
	private JLabel carFacing,iniX,iniY,iniF,stringCommandLabel;
	public Main (){// start of the constructor of the main frame 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("RC Car");
		setResizable(false); 
		setLayout(new FlowLayout());// the frame is flow layout becuase i use other layouts to do fine work on the panels
		add(road);
		JPanel buttonPanel= new JPanel(new GridBagLayout()); // this is the panel is with all of the buttons on it 
		GridBagConstraints buttonC=new GridBagConstraints();// this this makes grid for me to place all of the buttons on 
		carFacing= new JLabel("Car is facing South"); // creating all of the buttons and labels while then putting them on the correct panels
		buttonC.gridx=0;// the button names and label names are mostly speak for themselves
		buttonC.gridy=0;
		buttonPanel.add(carFacing, buttonC);
		b1= new JButton("Foward");
		buttonC.gridx=0;
		buttonC.gridy=1;
		buttonPanel.add(b1, buttonC);
		b2= new JButton("Back");
		buttonC.gridx=0;
		buttonC.gridy=2;
		buttonPanel.add(b2, buttonC);
		b3= new JButton("Turn Left");
		buttonC.gridx=0;
		buttonC.gridy=3;
		buttonPanel.add(b3, buttonC);
		b4= new JButton("Turn Right");
		buttonC.gridx=0;
		buttonC.gridy=4;
		buttonPanel.add(b4, buttonC);
		b7= new JButton("Set inital Car and Facing");
		buttonC.gridx=0;
		buttonC.gridy=5;
		buttonPanel.add(b7, buttonC);
		tfix= new JTextField(2);
		buttonC.gridx=1;
		buttonC.gridy=6;
		buttonPanel.add(tfix, buttonC);
		iniX= new JLabel("Inital X =");
		buttonC.gridx=0;
		buttonC.gridy=6;
		buttonPanel.add(iniX, buttonC);
		tfiy= new JTextField(2);
		buttonC.gridx=1;
		buttonC.gridy=7;
		buttonPanel.add(tfiy, buttonC);
		iniY= new JLabel("Inital Y =");
		buttonC.gridx=0;
		buttonC.gridy=7;
		buttonPanel.add(iniY, buttonC);
		tfif= new JTextField(1);
		buttonC.gridx=1;
		buttonC.gridy=8;
		buttonPanel.add(tfif, buttonC);
		iniF= new JLabel("Inital Facing =");
		buttonC.gridx=0;
		buttonC.gridy=8;
		buttonPanel.add(iniF, buttonC);
		b5= new JButton("Make 100 random obstacles");
		buttonC.gridx=0;
		buttonC.gridy=9;
		buttonPanel.add(b5, buttonC);
		b6= new JButton("Clear obstacles");
		buttonC.gridx=0;
		buttonC.gridy=10;
		buttonPanel.add(b6, buttonC);
		stringCommandLabel= new JLabel("Enter commands for the Car (f,b,l,r)"); 
		buttonC.gridx=0;
		buttonC.gridy=11;
		buttonPanel.add(stringCommandLabel, buttonC);
		tfStringCommand= new JTextField(25);
		buttonC.gridx=0;
		buttonC.gridy=12;
		buttonPanel.add(tfStringCommand, buttonC);
		b8= new JButton("Run String");
		buttonC.gridx=0;
		buttonC.gridy=13;
		buttonPanel.add(b8, buttonC);
		add(buttonPanel);// end of the grand block of button, text field, and label creation 
		pack();
		responceToClick handler = new responceToClick();// making the object that allows the buttons to work when hit 
		b1.addActionListener(handler);
		b2.addActionListener(handler); 
		b3.addActionListener(handler);
		b4.addActionListener(handler);
		b5.addActionListener(handler);
		b6.addActionListener(handler);
		b7.addActionListener(handler);
		b8.addActionListener(handler);
		setLocationRelativeTo(null);
		setVisible(true);
		JOptionPane.showMessageDialog( Main.this, String.format("Welcome to the Rc Car the buttons should speak for themselves.\n "
				+ "Things to note though would be  that the facing text feild requires an uppercase letter and the text field for\n"
				+ "the string command is in lower case with f being foward, b being back, l being turn left, r being\n"
				+ "turn right and spces are ignored"));// a small intro to the program built around the idea that random user is running the program
	}
	public void foward(){// most of these methods are links to the grid object so as to have power over the other objects that are only in this grid object
		this.road.carFoward();
	}
	public void back(){
		this.road.carBack();
	}
	public void turnLeft(){
		this.road.carLeft();
		updatefacinglabel();
	}
	public void turnRight(){
		this.road.carRight();
		updatefacinglabel();
	}
	public void makeObstacles(){
		this.road.generateObstacles();
	}
	public void clearObstacles(){
		this.road.destroyObstacles();
	}
	public void setCar(){// this methods takes the information from the correct text fields to inform the car object in the road object 
		int initalX=Integer.parseInt(tfix.getText());
		int initalY=Integer.parseInt(tfiy.getText());
		String initalFacing= tfif.getText();
		this.road.initalCar(initalX,initalY,initalFacing);
		updatefacinglabel();
	}
	public void runningString(){// this methods takes the text from the tfStringCommand text field then breaks it up into an array and then runs through the array 
		String input= tfStringCommand.getText(); //moving the car with each token in the array only stopping with the array is spanned or the car hit a block
		String[] tokens= input.split("");
		for (String token:tokens){
		      if (token.equals("f")){
		    	foward(); 
		      	}
		      else if (token.equals("b")){
			    back(); 
		      }
		      else if (token.equals("l")){
		    	turnLeft(); 
		      }
		      else if (token.equals("r")){
				turnRight(); 
		      }
		      else if (token.equals(" ")){
		    	  continue; 
		      }
		      else {
		    	  JOptionPane.showMessageDialog( Main.this, String.format("This is not a Proper string of actions ending at last valid move"));
		    	  break;
		      }
		      if (road.stringBreak==1){// this values is what cause the string to stop when it gets changed in the road object
		    	break;
		      }
		 }
    }
	public void updatefacinglabel(){// this method gets the current facing of the car object to then update label that infroms the user the current facing of the car
		String currentFacing =this.road.facingLabel(); 
		switch (currentFacing){
		case ("N"):
			this.carFacing.setText("Car is facing North");
			break;
		case ("S"):
			this.carFacing.setText("Car is facing South");
			break;
		case ("W"):
			this.carFacing.setText("Car is facing West");
			break;
		case ("E"):
			this.carFacing.setText("Car is facing East");
			break;
		}
	}
	public class responceToClick implements ActionListener
     {
     	public void actionPerformed (ActionEvent event)
     	{
     		String whatButton= new String (event.getActionCommand());
// so this objest allows my buttons to call methods when they are pressed 
     			switch(whatButton){
     			case "Foward":
     				foward();
     				break; 
     			case "Back":
     				back();
     				break; 
     			case "Turn Left":
     				turnLeft(); 
     				break; 
     			case "Turn Right":
     				turnRight();
     				break; 
     			case "Make 100 random obstacles":
     				makeObstacles();
     				break;
     			case "Clear obstacles":
     				clearObstacles();
     				break;
     			case "Set inital Car and Facing":
     				setCar();
     				break;
     			case "Run String":
     				runningString(); 
     				break; 
     		    default: 
     			}
     		
     	}
     }
	
	public static void main(String[] args) {
		new Main();// and to think all of this is started by just making a new mian. 
	}

}
