package gameState;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.game;
import ui.MenuButton;
import ultilz.LoadSave;

public class Menu extends state  implements stateMethod{
	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImg, backgroundImgPink;
	private int menuX, menuY, menuWidth, menuHeight;
	
	public Menu(game Game) {
		super(Game);
		loadButtons();
		loadBackground();
		backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int)(backgroundImg.getWidth() * game.SCALE); 
		menuHeight = (int)(backgroundImg.getHeight() * game.SCALE); 
		menuX = game.GAME_WIDTH/2 - menuWidth /2;
		menuY = (int) (45 * game.SCALE);
	}

	private void loadButtons() {
		buttons[0] = new MenuButton(game.GAME_WIDTH/2, (int) (150* game.SCALE) , 0, gameState.PLAYING);
		buttons[1] = new MenuButton(game.GAME_WIDTH/2, (int) (220* game.SCALE) , 1, gameState.OPTIONS);
		buttons[2] = new MenuButton(game.GAME_WIDTH/2, (int) (290* game.SCALE) , 2, gameState.QUIT);
		
	}

	@Override
	public void update() {
		for (MenuButton mb: buttons) {
			mb.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImgPink, 0, 0, game.GAME_WIDTH, game.GAME_HEIGHT,  null);
		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
		for (MenuButton mb: buttons) {
			mb.draw(g);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb: buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
				break;
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb: buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGameState();
				if (mb.getState() == gameState.PLAYING)
					Game.getAudioPlayer().serLevelSong(Game.getPlaying().getLevelManager().getLvIndex());
				break;
			}
		}
		resetButtons();
		
	}

	private void resetButtons() {
		for (MenuButton mb: buttons) {
			mb.resetBools();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (MenuButton mb: buttons) {
			mb.setMouseOver(false);
		}
		
		for (MenuButton mb: buttons) {
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}	
		}
	}
	
	
	// khi nhan enter thi state chuyen sang state = playing
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			gameState.state = gameState.PLAYING;
		
	}

	@Override
	public void keyRealeased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
