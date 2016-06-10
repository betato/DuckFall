package com.betato.duckfall;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {
	public Entity(BufferedImage texture, int height, int width) {
		// Scale image only if necessary
		if (height == 0 || width == 0 || width == texture.getWidth() 
				&& height == texture.getHeight()) {
			this.texture = scaleImage(texture, width, height);
		} else {
			this.texture = texture;
		}
		
		this.width = width;
		this.height = height;
	}

	private BufferedImage scaleImage(BufferedImage image, int width, int height) {
		// Get dimensions
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		double scaleX = (double) width / imageWidth;
		double scaleY = (double) height / imageHeight;

		// Transform Image
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
		return bilinearScaleOp.filter(image, new BufferedImage(width, height, image.getType()));
	}

	public int x;
	public int y;
	public int height;
	public int width;
	public ArrayList<ArrayList<Point>> collisionPoints = new ArrayList<ArrayList<Point>>();

	private BufferedImage texture;

	public void draw(Graphics g) {
		// Draw texture with x and y as center point
		g.drawImage(texture, x - width / 2, y - height / 2, null);
	}
}
