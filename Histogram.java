// BV Ue4 WS2025/26 Vorgabe
//
// Copyright (C) 2025 by Klaus Jung
// All rights reserved.
// Date: 2025-09-29
 		   		   	 	

package bv_ws2526;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Histogram {
 		   		   	 	
	private static final int grayLevels = 256;
	
    private GraphicsContext gc;
    private int maxHeight;
    
    private int[] histogram = new int[grayLevels];
 		   		   	 	
    public Histogram() {
	}
    
	public Histogram(GraphicsContext gc, int maxHeight) {
		this.gc = gc;
		this.maxHeight = maxHeight;
	}
	
	public int[] getValues() {
		return histogram;
	}

	public void setImageRegion(RasterImage image, int regionStartX, int regionStartY, int regionWidth, int regionHeight) {
		// TODO: calculate histogram[] out of the gray values found the given image region

        // Clear previous histogram
        for (int i = 0; i < grayLevels; i++) {
            histogram[i] = 0;
        }

        int[] argb = image.argb;
        int imgWidth = image.width;
        int imgHeight = image.height;

        // Ensure region is within image boundaries
        int startX = Math.max(0, regionStartX);
        int startY = Math.max(0, regionStartY);
        int endX = Math.min(imgWidth, startX + regionWidth);
        int endY = Math.min(imgHeight, startY + regionHeight);

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                int pixel = argb[y * imgWidth + x];
                int gray = (pixel >> 16) & 0xFF; // r == g == b in grayscale
                histogram[gray]++;
            }
        }
	}

	public Integer getMinimum() {
		// Will be used in Exercise 5.
		return null;
	}

	public Integer getMaximum() {
		// Will be used in Exercise 5.
		return null;
	}

	public Double getMean() {
		// Will be used in Exercise 5.
		return null;
	}

	public Integer getMedian() {
		// Will be used in Exercise 5.
		return null;
	}

	public Double getVariance() {
		// Will be used in Exercise 5.
		return null;
	}

	public Double getEntropy() {
		// Will be used in Exercise 5.
		return null;
	}

	public void draw(Color lineColor) {
		if(gc == null) return;
		gc.clearRect(0, 0, grayLevels, maxHeight);
		gc.setStroke(lineColor);
		gc.setLineWidth(1);

		// TODO: draw histogram[] into the gc graphic context
		// Note that we need to add 0.5 to all coordinates to align points to pixel centers

        // Find maximum value for scaling
        int maxValue = 0;
        for (int val : histogram) {
            if (val > maxValue) maxValue = val;
        }
        if(maxValue == 0) maxValue = 1; // prevent division by zero

        double shift = 0.5;

        for (int x = 0; x < grayLevels; x++) {
            double normalizedHeight = ((double) histogram[x] / maxValue) * maxHeight;
            gc.strokeLine(x + shift, maxHeight - normalizedHeight + shift, x + shift, maxHeight + shift);
        }

		// Remark: This is some dummy code to give you an idea for line drawing
		//gc.strokeLine(shift, shift, grayLevels-1 + shift, maxHeight-1 + shift);
		//gc.strokeLine(grayLevels-1 + shift, shift, shift, maxHeight-1 + shift);
		
	}
 		   		   	 	
}
 		   		   	 	






