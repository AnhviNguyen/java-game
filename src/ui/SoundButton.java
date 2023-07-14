package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static ultilz.constants.UI.PauseButtons.*;
import ultilz.LoadSave;

public class SoundButton extends PauseButton{
	
	private BufferedImage[][] soundImg;
	private boolean mouseOver, mousrPressed;
	private boolean muted;
	private int rowIndex, colIndex;

	public SoundButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		loadSoundImg();
	}

	// tai tung tam anh tu anh lon va luu tru chung trng array
	private void loadSoundImg() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTON);
		soundImg = new BufferedImage[2][3];
		for (int j=0; j< soundImg.length; j++) {
			for (int i=0; i< soundImg[j].length; i++) {
				soundImg[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT, j*SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
			}
		}	
	}
	
	// kiem tra xem co tat tieng hay khong. co thi xuong dong tuong ung voi anh, tuong tu voi cac hieu ung hover, click
	public void update() {
		if (muted) 
			rowIndex =1;
		else
			rowIndex =0;
		
		colIndex =0;
		if (mouseOver)
			colIndex =1;
		if (mousrPressed)
			colIndex =2;	 
	}
	
	public void resetBools() {
		mouseOver = false;
		mousrPressed = false;
	}
	// ve chung tren frame
	public void draw(Graphics g) {
		g.drawImage(soundImg[rowIndex][colIndex], x, y, width, height, null);
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public boolean isMousrPressed() {
		return mousrPressed;
	}

	public boolean isMuted() {
		return muted;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public void setMousrPressed(boolean mousrPressed) {
		this.mousrPressed = mousrPressed;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

}
