package com.praeses.interviews.rcchallenge;

import java.awt.*;
import javax.swing.*;

public class Animate {

	public static void main(String[] args) {
	
	
	
	}
	
	public Animate () {
	
		createWindow();
	
	}

	private static void createWindow() {
	
		JFrame frame = new JFrame("RC Challenge");
		frame.setBounds(0,0,1000,1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		JLabel label = new JLabel("I'm a label in the window",SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(300, 100));
		
		panel.add(label);
		
		frame.add(panel);
		frame.setSize(300,300);		
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	
	}

}
