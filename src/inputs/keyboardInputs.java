package inputs;

import java.awt.event.KeyEvent;
import static ultilz.constants.Direction.*;
import java.awt.event.KeyListener;

import gameState.gameState;
import main.gamePanel;

public class keyboardInputs implements KeyListener{
	private gamePanel gamepanel;
	

	public keyboardInputs(gamePanel gamepanel) {
		this.gamepanel = gamepanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	// cac su kien khi nhan vao ban phim
	@Override
	public void keyPressed(KeyEvent e) {
		switch (gameState.state) {
		case MENU:
			gamepanel.getGame().getMenu().keyPressed(e);
			break;
		case PLAYING:
			gamepanel.getGame().getPlaying().keyPressed(e);
			break;
		case OPTIONS:
			gamepanel.getGame().getGameOption().keyPressed(e);
			break;
		default:
			break;
		}
		
	}
		
	// su kien khi sau khi nhan ban phim (giai doan nha ra)
	@Override
	public void keyReleased(KeyEvent e) {
		switch (gameState.state) {
		case MENU:
			gamepanel.getGame().getMenu().keyRealeased(e);
			break;
		case PLAYING:
			gamepanel.getGame().getPlaying().keyRealeased(e);
			break;
		default:
			break;
		}
	}
		

}
