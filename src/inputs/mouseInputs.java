package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gameState.gameState;
import main.gamePanel;


// MouseListener: xu ly cac su kien cua chuot: click, tha
// MouseMotionListener: xu ly cac thao tac va chuyen dong cua chuot
public class mouseInputs implements MouseListener, MouseMotionListener{
	private gamePanel gamepanel;
	
	// contructor cua class
	public mouseInputs(gamePanel gamepanel) {
		this.gamepanel = gamepanel;
	}
	
	// su kien khi mouse dang keo o mot thanh phan nao do
	@Override
	public void mouseDragged(MouseEvent e) {
		switch (gameState.state) {
		case PLAYING:
			gamepanel.getGame().getPlaying().mouseDragged(e);
			break;
		case OPTIONS:
			gamepanel.getGame().getGameOption().mouseDragged(e);
			break;
		default:
			break;
		}
		
	}
	
	
	// su kien khi con chuot dang di chuyen o trong mot thanh phan nao do
	@Override
	public void mouseMoved(MouseEvent e) {
		switch (gameState.state) {
		case MENU:
			gamepanel.getGame().getMenu().mouseMoved(e);
			break;
		case PLAYING: 
			gamepanel.getGame().getPlaying().mouseMoved(e);
			break;
		case OPTIONS:
			gamepanel.getGame().getGameOption().mouseMoved(e);
			break;
		default:
			break;
		}
		
	}

	// su kien khi con chuot click vao mot thanh phan nao di
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (gameState.state) {
		case PLAYING:
			gamepanel.getGame().getPlaying().mouseClicked(e);
			break;
		case OPTIONS:
			gamepanel.getGame().getGameOption().mouseClicked(e);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (gameState.state) {
		case MENU:
			gamepanel.getGame().getMenu().mousePressed(e);
			break;
		case PLAYING:
			gamepanel.getGame().getPlaying().mousePressed(e);
			break;
		case OPTIONS:
			gamepanel.getGame().getGameOption().mousePressed(e);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (gameState.state) {
		case MENU:
			gamepanel.getGame().getMenu().mouseReleased(e);
			break;
		case PLAYING:
			gamepanel.getGame().getPlaying().mouseReleased(e);
			break;
		case OPTIONS:
			gamepanel.getGame().getGameOption().mouseReleased(e);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
