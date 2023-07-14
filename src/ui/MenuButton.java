package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gameState.gameState;
import ultilz.LoadSave;
import static ultilz.constants.UI.Buttons.*; 

public class MenuButton {
	private int xPos, yPos, rowIndex, index;
	private int xOffsetCenter = B_WIDTH/2;
	private gameState state;
	private BufferedImage[] img;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;
	
	
	// contructor cua menu button
	public MenuButton(int xPos, int yPos, int rowIndex, gameState state) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;
		
		loadImg();
		initBounds();
	}

	private void initBounds() {
		bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
		
	}
	// tai tung nhom anh nho lai vao 1 array
	private void loadImg() {
		img = new BufferedImage[3];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTON);
		for (int i=0; i< img.length; i++) {
			img[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
		}
	}
	//  ve no vao frame, mac dinh ban dau la hinh anh co index =0
	public void draw(Graphics g) {
		g.drawImage(img[index], xPos - xOffsetCenter, yPos,  B_WIDTH, B_HEIGHT, null);
		
	}
	// cac hieu ung khi hover va click button
	public void update() {
		index =0; 
		if (mouseOver) {
			index =1;	
		}
		if (mousePressed) {
			index =2;
		}
	}

	public boolean isMouseOver() {
		return mouseOver;
	}
	// tao 1 bounds bao quanh nut button de de chinh sua ( cu the la kiem tra chuot xem no co nam trong de nhan biet ma hover voi click no) 
	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
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
	
	public void applyGameState() {
		gameState.state = state;
	}
	
	public void resetBools() {
		mouseOver  = false;
		mousePressed = false;
	}
	
	public gameState getState() {
		return state;
	}
}
