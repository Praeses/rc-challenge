package com.praeses.interviews.rcchallenge;

import java.awt.*;
import javax.swing.*;

public class GridFrame extends JFrame {

	JPanel panel;
	public GridComponent component;
	private static final int FRAME_WIDTH = 1000;
	private static final int FRAME_HEIGHT = 1000;

	public GridFrame () {
	
		createPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		component = new GridComponent(100, 100);
		panel.add(component);
		panel.updateUI();
	
	}
	
	private void createPanel() {
	
		panel = new JPanel();
		
		add(panel);
	
	}
	
	

}
