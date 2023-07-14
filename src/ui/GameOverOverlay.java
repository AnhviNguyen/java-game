package ui;

import static ultilz.constants.UI.URMButtons.URM_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gameState.Playing;
import gameState.gameState;
import main.game;
import ultilz.LoadSave;

public class GameOverOverlay {
	private Playing playing;
	private BufferedImage img;
	private int imgX, imgY, imgW, imgH;
	private UrmButton menu, play;
	
	// contructor
	public GameOverOverlay(Playing playing) {
		this.playing = playing;
		createImg();
		createButtons();
	}
	// tao button 
	private void createButtons() {
		int menuX = (int) (335 * game.SCALE);
		int playX = (int) (440 * game.SCALE);
		int y = (int) (195 * game.SCALE);
		// rowindex tuong ung voi vi tri trong anh
		play = new UrmButton(playX, y, URM_SIZE, URM_SIZE, 0);
		menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
		
	}
	// anh  duoc luu ve  (anh lua chon khi dead)
	private void createImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
		imgW = (int)(game.SCALE * img.getWidth());
		imgH = (int) (game.SCALE * img.getHeight());
		imgX = game.GAME_WIDTH / 2- imgW /2;
		imgY = (int) (100* game.SCALE);
		
	}
	// sua khi user nhan escape de reset game va toi thanh menu
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			playing.resetAll();
			gameState.state  = gameState.MENU;
		}
	}
	
	public void draw(Graphics g) {
		// tao vg mau xam 
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, game.GAME_WIDTH, game.GAME_HEIGHT);
		// ve len tren do mot form img 
		g.drawImage(img, imgX, imgY, imgW, imgH, null);
		// ve tiep len form nut play va menu
		menu.draw(g);
		play.draw(g);
	}
	
	// thiet lap ban dau den false ( moi lan mouse di chuyen thi cac gia tri deu false)
	public void mouseMoved(MouseEvent e) {
		play.setMouseOver(false);
		menu.setMouseOver(false);

		if (isIn(menu, e))
			menu.setMouseOver(true);
		else if (isIn(play, e))
			play.setMouseOver(true);
	}
	
	// cac truong hop khi nhan button menu va button play
	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) {
			if (menu.isMousePressed()) {
				playing.resetAll();
				playing.setGameState(gameState.MENU);

			}
		} else if (isIn(play, e))
			if (play.isMousePressed()) {
				playing.resetAll();
				playing.getGame().getAudioPlayer().serLevelSong(playing.getLevelManager().getLvIndex());
			}
				
		
		// cac gia tri nhu ismousepressed duoc reset lai
		menu.resertBools();;
		play.resertBools();
	}
	
	// cac hieu ung khi mouse duoc nhan
	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e))
			menu.setMousePressed(true);
		else if (isIn(play, e))
			play.setMousePressed(true);
	}
	
	// kiem tra xem cac nut ma mouse di toi co nam trong pham vi cu no hay khong (su dung retangle trong java swing)
	private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
	// hieu ung khi hover va clicj cac nut
	public void update() {
		menu.update();
		play.update();
	}
}
