package audio;

import java.awt.datatransfer.Clipboard;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	public static int MENU_1 =0;
	public static int LEVEL_1 = 1;
	public static int LEVEL_2= 2;
	
	public static int DIE =0;
	public static int JUMP= 1;
	public static int GAMEOVER = 2;
	public static int LV_COMPLETED = 3;
	public static int ATTACK_ONE =4;
	public static int ATTACK_TWO =5;
	public static int ATTACK_THREE =6;
	
	private Clip[] songs, effects;
	private int currentSongId;
	private float volume = 1f;
	private boolean songMute, effectMute;
	private Random rand = new Random();
	
	
	public AudioPlayer() {
		loadSong();
		loadEffect();
		playSong(MENU_1);
	}
	
	private void loadSong() {
		String[] names = {"menu", "level1", "level2"};
		songs = new Clip[names.length];
		for (int i=0; i< songs.length; i++) {
			songs[i]= getClip(names[i]);
		}
	}
	
	private void loadEffect() {
		String[] effectNames = {"die", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3"};
		effects = new Clip[effectNames.length];
		for (int i=0; i< effects.length; i++) {
			effects[i] = getClip(effectNames[i]);
		}
		
		updateEffectsVolume();
	}
	
	// truy cap de lay file (input stream)
	private Clip getClip(String name) {
		URL url = getClass().getResource("/audio/" + name + ".wav");
		AudioInputStream audio;
		
		try {
			// truy cap file de lay file am thanh 
			audio = AudioSystem.getAudioInputStream(url);
			// doan am thanh duoc phat ra va co the kiem soat (tat, mo)
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	// cai dat tat, mo am thanh (nhac)
	public void toggueSongMute() {
		this.songMute = !songMute;
		for (Clip c : songs) {
			// dang du lieu dung de kiem tra am thanh do co tat hay mo tieng
			BooleanControl booleanControl = (BooleanControl)c.getControl(BooleanControl.Type.MUTE);
			// gan gia tri cua songmute de kiem soat am thanh
			booleanControl.setValue(songMute);
			
		}
	}
	
	// cai dat am thanh chuyen dong
	public void toggueEffectMute() {
		this.effectMute = !songMute;
		for (Clip c : effects) {
			BooleanControl booleanControl = (BooleanControl)c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(effectMute);
		}
		if (!effectMute)
			playEffect(JUMP);	
	}
	
	
	// thiet lap gia tri cho volume
	public void setVolume(float volume) {
		this.volume = volume;
		updateSongValue();
		updateEffectsVolume();
	}
	
	
	// dung nhac 
	public void stopSong() {
		if (songs[currentSongId].isActive()) {
			songs[currentSongId].stop();
		}
	}
	
	
	// thiet lap nhac theo level xen ke nhau
	public void serLevelSong(int lvIndex) {
		if (lvIndex % 2 ==0) {
			playSong(LEVEL_1);
		}else {
			playSong(LEVEL_2);
		}
	}
	
	
	// dung nhac lai va tai nhac khi hoan thanh game
	public void lvComplete() {
		stopSong();
		playEffect(LV_COMPLETED);
	}
	
	//thiet lap am thanh khi nhan vat tan cong (theo random)
	public void playAttackSound() {
		int start = 4;
		start += rand.nextInt(3);
		playEffect(start);
	}
	
	// phat hieu ung khi choi game
	public void playEffect(int effect) {
		//vi tri bat dau phat nhac
		effects[effect].setMicrosecondPosition(0);
		effects[effect].start();
	}
	
	// phat nhac khi choi game
	public void playSong(int song) {
		stopSong();
		
		currentSongId = song;
		updateSongValue();
		songs[currentSongId].setMicrosecondPosition(0);
		// nhac se duoc lap lai cho den khi gap lenh stop
		songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
	}

	// update gia tri cua song
	private void updateSongValue() {
		FloatControl gaincontrol = (FloatControl)songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gaincontrol.getMaximum() -gaincontrol.getMinimum();
		float gain = (range * volume)+ gaincontrol.getMinimum();
		gaincontrol.setValue(gain);	
	}
	
	
	// update gia tri cua effect
	private void updateEffectsVolume() {
		for (Clip c: effects) {
			// float control: dung de hieu chinh am thanh duoi dang so thuc tra ve kieu so thuc
			FloatControl gaincontrol = (FloatControl)c.getControl(FloatControl.Type.MASTER_GAIN);
			// khoang gia tri am thanh
			float range = gaincontrol.getMaximum() -gaincontrol.getMinimum();
			// hieu chinh lai am thanh ve set
			float gain = (range * volume)+ gaincontrol.getMinimum();
			gaincontrol.setValue(gain);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
