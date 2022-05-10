package main;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {
	
    public static BufferedImage grass;
	public static BufferedImage dirt;
    public static BufferedImage stone;

	public static BufferedImage[] playerAir = new BufferedImage[4];
	public static BufferedImage[] playerRunning = new BufferedImage[8];
	public static BufferedImage[] playerIdle = new BufferedImage[12];

	public static BufferedImage[] background = new BufferedImage[5];

	public Images() {
		try {
            grass = ImageIO.read(new FileInputStream("assets/blocks/grass.png"));
			dirt = ImageIO.read(new FileInputStream("assets/blocks/dirt.png"));
            stone = ImageIO.read(new FileInputStream("assets/blocks/stone.png"));

			for (int i = 0; i < 4; i++) playerAir[i] = ImageIO.read(new FileInputStream("assets/player/air/air-" + (i + 1) + ".png"));
			for (int i = 0; i < 8; i++) playerRunning[i] = ImageIO.read(new FileInputStream("assets/player/running/run-" + (i + 1) + ".png"));
			for (int i = 0; i < 12; i++) playerIdle[i] = ImageIO.read(new FileInputStream("assets/player/idle/idle-" + (i + 1) + ".png"));

			for (int i = 0; i < 5; i++) background[i] = ImageIO.read(new FileInputStream("assets/background/bg-" + (i + 1) + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 }