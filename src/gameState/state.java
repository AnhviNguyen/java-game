package gameState;

import java.awt.event.MouseEvent;

import audio.AudioPlayer;
import main.game;
import ui.MenuButton;

public class state {
	protected game Game;
	
	public state(game Game) {
		this.Game = Game;
	}

	public game getGame() {
		return Game;
	}

	public void setGame(game game) {
		Game = game;
	}
	
	//  liem tra xem co nam trong cai mb hay khoong
	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}
	
	// thiet lap am thanh ung voi state tuong ung
	public void setGameState(gameState state) {
		switch(state) {
		case MENU -> Game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
		case PLAYING -> Game.getAudioPlayer().serLevelSong(Game.getPlaying().getLevelManager().getLvIndex());
		}
		
		gameState.state = state;
	}
	
}
