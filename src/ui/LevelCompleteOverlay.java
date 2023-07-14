package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gameState.Playing;
import gameState.gameState;
import main.game;
import ultilz.LoadSave;
import static ultilz.constants.UI.URMButtons.*;

public class LevelCompleteOverlay {
	private Playing playing;
	private UrmButton menu, next;
	private BufferedImage img;
	private int bgX, bgY, bgW, bgH;
	

	public LevelCompleteOverlay(Playing playing) {
		this.playing = playing;
		initImg();
		initButton();
	}


	private void initButton() {
		int menuX = (int) (330 * game.SCALE);
		int nextX = (int) (445 * game.SCALE);
		int y = (int) (195 * game.SCALE);
		next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
		menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
		
	}


	private void initImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETE_IMG);
		bgW = (int) (img.getWidth() * game.SCALE);
		bgH = (int) (img.getHeight() * game.SCALE);
		bgX = game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (75 * game.SCALE);
		
	}
	
	public void update() {
		next.update();
		menu.update();
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, game.GAME_WIDTH, game.GAME_HEIGHT);

		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		next.draw(g);
		menu.draw(g);
	}
	
	public void mouseMoved(MouseEvent e) {
		next.setMouseOver(false);
		menu.setMouseOver(false);

		if (isIn(menu, e))
			menu.setMouseOver(true);
		else if (isIn(next, e))
			next.setMouseOver(true);
	}
	
	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) {
			if (menu.isMousePressed()) {
				playing.resetAll();
				playing.setGameState(gameState.MENU);
			}
		} else if (isIn(next, e))
			if (next.isMousePressed()) {
				playing.loadNextLevel();
				playing.getGame().getAudioPlayer().serLevelSong(playing.getLevelManager().getLvIndex());
			}
				

		menu.resertBools();;
		next.resertBools();
	}
	
	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e))
			menu.setMousePressed(true);
		else if (isIn(next, e))
			next.setMousePressed(true);
	}
	
	private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
}
