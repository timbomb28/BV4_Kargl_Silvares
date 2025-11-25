// BV Ue4 WS2025/26 Vorgabe
//
// Copyright (C) 2025 by Klaus Jung
// All rights reserved.
// Date: 2025-09-29
 		   		   	 	

package bv_ws2526;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ToneCurve {
 		   		   	 	
	private static final int grayLevels = 256;
	
    private GraphicsContext gc;
    
    private int[] grayTable = new int[grayLevels];
 		   		   	 	
	public int[] getGrayTable() {
		return grayTable;
	}

	public ToneCurve(GraphicsContext gc) {
		this.gc = gc;
	}
	
	public void updateTable(int brightness, double contrast, double gamma) {
		
		// TODO: Fill the grayTable[] array to map gray input values to gray output values.
		// It will be used as follows: grayOut = grayTable[grayIn].
		//
		// Use brightness, contrast, and gamma settings.
		//
		// See "Gammakorrektur" at slide no. 20 of 
		// http://home.htw-berlin.de/~barthel/veranstaltungen/GLDM/vorlesungen/04_GLDM_Bildmanipulation1_Bildpunktoperatoren.pdf
		//
		// First apply the brightness change, afterwards the contrast modification and finally the gamma correction.

        for (int grayIn = 0; grayIn < grayLevels; grayIn++) {

            // brightness adjustment
            double value = grayIn + brightness;

            value = (value - 128) * contrast + 128;

            double normalized = value / 255.0;
            if(normalized < 0) normalized = 0;
            if(normalized > 1) normalized = 1;
            normalized = Math.pow(normalized, 1/gamma);

            int grayOut = (int)(normalized * 255.0);

            if(grayOut < 0) grayOut = 0;
            if(grayOut > 255) grayOut = 255;

            grayTable[grayIn] = grayOut;
        }
	}

	public void applyTo(RasterImage image) {

		// TODO: apply the gray value mapping to the given image

        int[] argb = image.argb;

        for (int i = 0; i < argb.length; i++) {
            int pixel = argb[i];

            int a = (pixel >> 24) & 0xFF;
            int r = (pixel >> 16) & 0xFF;
            int g = (pixel >> 8) & 0xFF;
            int b = pixel & 0xFF;

            int grayIn = r;

            int grayOut = grayTable[grayIn];

            int newPixel = (a << 24) | (grayOut << 16) | (grayOut << 8) | grayOut;
            argb[i] = newPixel;
        }
	}

	public void draw(Color lineColor) {
		if(gc == null) return;
		gc.clearRect(0, 0, grayLevels, grayLevels);
		gc.setStroke(lineColor);
		gc.setLineWidth(3);

		// TODO: draw the tone curve into the gc graphic context
		// Note that we need to add 0.5 to all coordinates to align points to pixel centers

        gc.beginPath();
        double shift = 0.5;

        gc.moveTo(0 + shift, (grayLevels - 1 - grayTable[0]) + shift);

        for (int x = 1; x < grayLevels; x++) {
            double y = grayLevels - 1 - grayTable[x];
            gc.lineTo(x + shift, y + shift);
        }

        gc.stroke();

        // Remark: This is some dummy code to give you an idea for graphics drawing using paths
        /*
        gc.beginPath();
        gc.moveTo(64 + shift, 128 + shift);
        gc.lineTo(128 + shift, 192 + shift);
        gc.lineTo(192 + shift, 64 + shift);
        gc.stroke();

         */
	}

 		   		   	 	
}
 		   		   	 	




