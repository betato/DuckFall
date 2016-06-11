package com.betato.duckfall;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class TextureLoader {

	private HashMap<String, BufferedImage> textures;
	
	public TextureLoader() {
		textures = new HashMap<>();
	}
	
	public BufferedImage getTexture(String name) {
		if (!textures.containsKey(name)) {
			textures.put(name, loadTexture(name));
		}
		return textures.get(name);
	}

	private BufferedImage loadTexture(String name) {
		URL path = this.getClass().getClassLoader().getResource("/resource/texture/" + name);
		BufferedImage image = null;
		try {
			image = ImageIO.read(path);
		} catch (IOException e) {
			System.out.println("IOException during loading " + name + ": ");
			e.printStackTrace();
		}
		return image;
	}
	
}
