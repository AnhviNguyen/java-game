package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ultilz.LoadSave;
import static ultilz.constants.UI.URMButtons.*;

public class UrmButton extends PauseButton{
	private BufferedImage[] img;
	private int rowIndex, index;
	private boolean mouseOver, mousePressed;
	// contructor cua button de thiet lap vi tri va dai rong cua no (bao gom tat ca cac nut dua tren rowindex)
	public UrmButton(int x, int y, int width, int height, int rowIndex) {
		super(x, y, width, height);
		this.rowIndex = rowIndex;
		loadImgs();
	}
	
	
	// load anh nhung button trong state menu
	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTON);
		// luu tru  3 trang thai cua nut ( chua hover, hover, click)
		img = new BufferedImage[3];
		for (int i=0; i< img.length; i++) {
			img[i] = temp.getSubimage(i *URM_DEFAULT_SIZE, rowIndex * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
		}
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}
	// gan giong nhu hover
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	// cac su kien khi nhan. hover mac dinh la trang thai ban dau ( index =0)
	public void update() {
		index =0;
		if (mouseOver)
			index =1;
		if (mousePressed)
			index =2;
	}
	
	// ve len trang thai cua 1 nut
	public void draw(Graphics g) {
		g.drawImage(img[index], x, y, URM_SIZE, URM_SIZE,  null); 
	}
	
	
	// reset lai gia tri
	public void resertBools() {
		mouseOver =false;
		mousePressed = false;
	}
	
	

}
