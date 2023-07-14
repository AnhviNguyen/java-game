package gameState;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.game;
import ui.AudioOptions;
import ui.PauseButton;
import ui.UrmButton;
import ultilz.LoadSave;
import static ultilz.constants.UI.URMButtons.*;

public class GameOptions extends state implements stateMethod{
	private AudioOptions audioOptions;
	private BufferedImage backgroundImg, optionBackgroundImg;
	private int bgX, bgY, bgW, bgH;
	private UrmButton menuB;
	
	
	public GameOptions(game Game) {
		super(Game);
		loadImg();
		loadButton();
		audioOptions = Game.getAudioOption();
	}
	
	
	// nut home trong state option
	private void loadButton() {
		int menuX = (int)( 387 * game.SCALE);
		int menuY = (int) (325* game.SCALE);
		
		menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
		
	}
	
	// tai anh tu file dax luu tru anh (background) trong loadsave
	private void loadImg() {
		// full man hinh  ( do thiet lap ban dau * scale)
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
		optionBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.OPTION_MENU);
		// thiet lap vi tri dai rong va vi tri cua optionBackgroundImg
		bgW = (int)(optionBackgroundImg.getWidth() * game.SCALE);
		bgH = (int) (optionBackgroundImg.getHeight() * game.SCALE);
		bgX = game.GAME_WIDTH /2 - bgW/2;
		bgY = (int) (33* game.SCALE);
	}

	@Override
	// update trong menuB dung de tao hieu ung hover, click khi di chuot
	public void update() {
		menuB.update();
		audioOptions.update();
	}

	@Override
	// ve le frame
	public void draw(Graphics g) {
		// ve bang voi kich thuoc cua game
		g.drawImage(backgroundImg, 0, 0, game.GAME_WIDTH, game.GAME_HEIGHT, null);
		// ve thoe huong tinh toan o tren
		g.drawImage(optionBackgroundImg, bgX, bgY, bgW, bgH, null);
		// ve nut munuB thong qua class urmbutton cua no
		menuB.draw(g);
		audioOptions.draw(g);
	}
	
	// kiem tra xem mouse co nam trong bounds cua button hay khong
	private boolean isIn(MouseEvent e, PauseButton b) {
		return (b.getBounds().contains(e.getX(), e.getY())) ;
	}
	
	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuB)) {
			menuB.setMousePressed(true);
		}
		else
			audioOptions.mousePressed(e);
		
	}

	@Override
	// neu menu b duoc nhan thi chuyen sang menu state
	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed())
				gameState.state = gameState.MENU;
		}else
			audioOptions.mouseReleased(e);
		
		menuB.resertBools();
	}

	@Override
	// neu mouse di chuyen toi bound thi set gia tri thanh true
	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);
		
		if (isIn(e, menuB)) {
			menuB.setMouseOver(true);
		}else {
			audioOptions.mouseMoved(e);
		}
		
	}

	// khi nhan esc thi tu dong chuyen sang menu
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState.state = gameState.MENU;
		}
		
	}

	@Override
	public void keyRealeased(KeyEvent e) {
		
	}

}
