package ui;

import static ultilz.constants.UI.PauseButtons.SOUND_SIZE;
import static ultilz.constants.UI.VolumeButtons.SLIDER_WIDTH;
import static ultilz.constants.UI.VolumeButtons.VOLUME_HEIGHT;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.game;

public class AudioOptions {
	private  VolumeButton volumeButton;
	private SoundButton musicButton, sfxButton;
	private game Game;
	
	// contructor cua class nay
	public AudioOptions(game Game) {
		this.Game = Game;
		createSounButton();
		createVolumeButton();
	}
	
	// thiet lap vi tri cua volume button voi toa do cu the ( thanh dai dieu chinh am thanh)
	private void createVolumeButton() {
		int vX = (int) (309 * game.SCALE);
		int vY = (int) (278 * game.SCALE);
		volumeButton = new VolumeButton(vX, vY,SLIDER_WIDTH, VOLUME_HEIGHT);
		
	}
	
	// toa do cua cac nut bat tat am thanh  va chieu dai rong cua no
	private void createSounButton() {
		int soundX = (int) (450* game.SCALE);
		int musicY = (int) ( 140* game.SCALE);
		int sfxY = (int) ( 186 *game.SCALE);
		
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
	}
	
	// hieu ung tuong ung voi cac nut
	public void update() {
		musicButton.update();
		sfxButton.update();
		volumeButton.update();
	}
	
	// ve len frame
	public void draw (Graphics g) {
		musicButton.draw(g);
		sfxButton.draw(g);
		//volume slider
		volumeButton.draw(g);
	}
	
	// keo tha chuot dung trong thanh dai dieu chinh am thanh
	public void mouseDragged(MouseEvent e) {
		if (volumeButton.isMousePressed()) {
			float valueBefore  = volumeButton.getFloatValue();
			volumeButton.changeX(e.getX());
			float valueAfter = volumeButton.getFloatValue();
			if ( valueBefore != valueAfter) {
				Game.getAudioPlayer().setVolume(valueAfter);
			}
		}
	}
	
	
	// kiem tra xem su kien mouse co nam trong pham vi cua bound da danh dau truoc do hay khong
	private boolean isIn(MouseEvent e, PauseButton b) {
		return (b.getBounds().contains(e.getX(), e.getY())) ;
	}

	// ckeck xem co nam trong hay khong ( co thi doi anh, khong thi thoi)
	public void mousePressed(MouseEvent e) {
		  if (isIn(e, musicButton))
			  musicButton.setMousrPressed(true);
		  else if (isIn(e, sfxButton))
			  sfxButton.setMousrPressed(true);
		  else if (isIn(e, volumeButton))
			  volumeButton.setMousePressed(true);
		  
	}

	// hieu chinh am thanh khi no nam trong ( co thi tat tieng, ko thi thoi)
	public void mouseReleased(MouseEvent e) {
		 if (isIn(e, musicButton)) {
			 if (musicButton.isMousrPressed()) {
				 musicButton.setMuted(!musicButton.isMuted());
				 Game.getAudioPlayer().toggueSongMute();
			 }
		 }
		 else if (isIn(e, sfxButton)) {
			 if (sfxButton.isMousrPressed()) {
				 sfxButton.setMuted(!sfxButton.isMuted());
				 Game.getAudioPlayer().toggueEffectMute();
			 }
		 }
		 //thiet lap lai cac gia tri ban dau
		 musicButton.resetBools();
		 sfxButton.resetBools();
	}

	// tinh cac gia tri ban dau bang false ( neu mouse di chuyen trong pham vi bound thi true)
	public void mouseMoved(MouseEvent e) {	
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		volumeButton.setMouseOver(false);
		
		  if (isIn(e, musicButton))
			  musicButton.setMouseOver(true);
		  else if (isIn(e, sfxButton))
			  sfxButton.setMouseOver(true);
		  else if (isIn(e, volumeButton))
			  volumeButton.setMouseOver(true);
		
	}

	
}
