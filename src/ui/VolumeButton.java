package ui;



import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ultilz.LoadSave;
import static ultilz.constants.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton{
	private BufferedImage[] img;
	private BufferedImage slider;
	private int index =0;
	private boolean mouseOver, mousePressed;
	private int buttonX, minX, maxX;
	private float floatValue = 0f;
	
	
	//?
	public VolumeButton(int x, int y, int width, int height) {
		super(x + width/2, y, VOLUME_WIDTH, height);
		bounds.x -= VOLUME_WIDTH/2;
		buttonX = x +width/2;
		this.x = x;
		this.width =width;
		minX = x + VOLUME_WIDTH/2;
		maxX = x +width - VOLUME_WIDTH/2;
		
		loadImgs();
	}
	
	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTON);
		//button dung de dieu chinh am thanh co 3 trang thai
		img = new BufferedImage[3];
		for (int i=0; i< img.length ; i++) {
			img[i] = temp.getSubimage(i * VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
		}
		// cai slider 
		slider = temp.getSubimage(3* VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	// thiet lap khi button duoc nhan, hover
	public void update() {
		index =0;  
		if (mouseOver)
			index= 1;
		if (mousePressed) {
			index =2;
		}
	}
	
	// thay doi toa do cua x
	public void changeX(int x) {
		if (x < minX) 
			buttonX = minX;
		else if (x > maxX)
			buttonX = maxX;
		else
			buttonX = x;
		updateFloatValue();
		bounds.x = buttonX - VOLUME_WIDTH/2;  //?
		
	}
	
	// chinh sua lai gia tri sau khi di chuyen button ( am thanh duoc hieu chinh trong khoang tu 0-1)
	private void updateFloatValue() {
		float range = maxX - minX;
		float value = buttonX - minX;
		floatValue = value/range;
		
	}

	// ve len frame
	public void draw(Graphics g) {
		g.drawImage(slider, x, y,width, height, null);
		g.drawImage(img[index], buttonX - VOLUME_WIDTH/2, y, VOLUME_WIDTH, height, null);
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}
	
	public float getFloatValue() {
		return floatValue;
	}

}
