package com.praeses.interviews.rcchallenge;

import java.awt.*;
import javax.swing.*;

public class GridComponent extends JComponent {

	int xDimension;
	int yDimension;
	int size;

	GridComponent(int xDim, int yDim) {
	
	xDimension = xDim;
	yDimension = yDim;
	size = 10;
	
	setPreferredSize(new Dimension(1000,1000));
	
	}
	
	public void paintComponent(Graphics g) {
	
	Graphics2D g2 = (Graphics2D) g;
	
	for (int i = 0; i < xDimension; i++) {
	
		for (int j = 0; j < yDimension; j++) {
	
			Rectangle grid = new Rectangle( i * size, j * size, size, size);
			
			g2.draw(grid);
	
		}
	
	}
	
	}

}
