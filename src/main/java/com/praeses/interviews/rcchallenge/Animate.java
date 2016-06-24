package com.praeses.interviews.rcchallenge;

import java.awt.*;
import javax.swing.*;

public class Animate {

	public static void main(String[] args) {
	
	createWindow();
	
	}

	private static void createWindow() {
	
		JFrame frame = new JFrame("RC Challenge");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel textLabel = new JLabel("I'm a label in the window",SwingConstants.CENTER);
		textLabel.setPreferredSize(new Dimension(300, 100));
		frame.getContentPane().add(textLabel, BorderLayout.CENTER);
		
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	
	}

}
