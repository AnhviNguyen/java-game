package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.sound.sampled.AudioPermission;

import gameState.Playing;
import gameState.gameState;
import main.game;
import ultilz.LoadSave;
import static ultilz.constants.UI.PauseButtons.*;
import static ultilz.constants.UI.URMButtons.*;
import static ultilz.constants.UI.VolumeButtons.*;

public class PauseOverlay {
	
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW, bgH;

	private UrmButton menuB, replayB, unpauseB;
	private Playing playing;
	private AudioOptions audioOptions;
	
	
	
	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		audioOptions = playing.getGame().getAudioOption();
		createUrmButton();
		
	}

	private void createUrmButton() {
		int menuX = (int) (313 * game.SCALE);
		int replayX = (int) (387 * game.SCALE);
		int unpausedX = (int) ( 462 * game.SCALE);
		int bY = (int)( 325 * game.SCALE);
		
		menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB = new UrmButton(unpausedX, bY, URM_SIZE, URM_SIZE, 0);
		
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgW =(int) (backgroundImg.getWidth() * game.SCALE);
		bgH =(int) (backgroundImg.getHeight() * game.SCALE);
		bgX = game.GAME_WIDTH/2 - bgW /2;
		bgY =(int)( 25 * game.SCALE) ;
		
		
	}

	public void update() {
		menuB.update();
		replayB.update();
		unpauseB.update();
		audioOptions.update();
	}
	
	public void draw (Graphics g) {
		//background 
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
		// Urm button
		menuB.draw(g);
		replayB.draw(g);;
		unpauseB.draw(g);
		audioOptions.draw(g);

	}
	
	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);
	}
	

	public void mousePressed(MouseEvent e) {
		  if (isIn(e, menuB))
			  menuB.setMousePressed(true);
		  else if (isIn(e, replayB))
			  replayB.setMousePressed(true);
		  else if (isIn(e, unpauseB))
			  unpauseB.setMousePressed(true);
		  else 
			 audioOptions.mousePressed(e);
		  
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) {
				playing.resetAll();
				playing.setGameState(gameState.MENU);
				playing.unpausedGame();
			}
		}
		else if (isIn(e, replayB)) {
			 if (replayB.isMousePressed()) {
				playing.resetAll();
				playing.unpausedGame();
			 }
		 }
		 else if (isIn(e, unpauseB)) {
			 if (unpauseB.isMousePressed()) {
				playing.unpausedGame();
			 }
		 }
		 else 
			 audioOptions.mouseReleased(e);
		
		 menuB.resertBools();
		 replayB.resertBools();
		 unpauseB.resertBools();
	}


	public void mouseMoved(MouseEvent e) {	
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		

		 if (isIn(e, menuB))
			  menuB.setMouseOver(true);
		  else if (isIn(e, replayB))
			  replayB.setMouseOver(true);
		  else if (isIn(e, unpauseB))
			  unpauseB.setMouseOver(true);
		  else 
			  audioOptions.mouseMoved(e);
		
	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return (b.getBounds().contains(e.getX(), e.getY())) ;
	}


}
