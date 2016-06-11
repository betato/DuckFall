package com.betato.duckfall;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {

	public int x;
	public int y;
	public int height;
	public int width;
	
	public int xVelocity;
	public int yVelocity;
	
	public ArrayList<Point> collisionPoints = new ArrayList<Point>();

	private BufferedImage texture;
	
	public Entity(BufferedImage texture, int width, int height) {
		// Scale image only if necessary
		if (height <= 1 || width <= 1 || width == texture.getWidth() 
				&& height == texture.getHeight()) {
			this.texture = texture;
			// Set width and height to image dimensions
			this.width = texture.getWidth();
			this.height = texture.getHeight();
		} else {
			this.texture = scaleImage(texture, width, height);
			// Set width and height to specified dimensions
			this.width = width;
			this.height = height;
		}
		
		// Record all transparent points
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (isPixelOpaque(this.texture, i, j)){
					collisionPoints.add(new Point(i, j));
				}
			}
		}
	}
	
	private boolean isPixelOpaque(BufferedImage image, int x, int y) {
		int pixel = image.getRGB(x, y);
		// Check alpha value
		if ((pixel >> 24) == 0x00) {
			return false;
		}
		return true;
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

	public void setPos(Point p) {
		x = p.x;
		y = p.y;
	}
	
	public Point getPos() {
		return new Point(x, y);
	}
	
	public void incrementPos(Point p) {
		x =+ p.x;
		y =+ p.y;
	}
	
	public void incrementPos(int x, int y) {
		this.x =+ x;
		this.y =+ y;
	}
	
	public void stepPos(){
		this.x =+ xVelocity;
		this.y =+ yVelocity;
	}
	
	public boolean isCollidingWith(Entity collidingEntity){
		// Check collision with axis-aligned bounding box first
		if (collidingEntity.x < x + width &&
				collidingEntity.x + collidingEntity.width > x &&
				collidingEntity.y < y + height &&
				collidingEntity.height + collidingEntity.y > y) {
			// Outer boxes are colliding, check individual points
			int xApart = x - collidingEntity.x;
			int yApart = y - collidingEntity.y;
			int collisionPointSize1 = collisionPoints.size();
			int collisionPointSize2 = collidingEntity.collisionPoints.size();
			for (int i = 0; i < collisionPointSize1; i++) {
				for (int j = 0; j < collisionPointSize2; j++) {
					// Check if any point is touching any other point
					if (collisionPoints.get(i).x + xApart == 
							collidingEntity.collisionPoints.get(j).x &&
							collisionPoints.get(i).y + yApart == 
							collidingEntity.collisionPoints.get(j).y){
						return true;
					}
				}
			}
		}
		return false;
	}

	public void draw(Graphics g) {
		// Draw texture with x and y as center point
		g.drawImage(texture, x - width / 2, y - height / 2, null);
	}
}
