package main;

import java.awt.Graphics;

import audio.AudioPlayer;
import gameState.GameOptions;
import gameState.Menu;
import gameState.Playing;
import gameState.gameState;
import ui.AudioOptions;
import ultilz.LoadSave;
// fps: so frame / 1s
// ups: so lan cap nhat trang thai lien tiep cua game/ 1s
public class game implements Runnable{
	private gameWindow gamewindow;
	private gamePanel gamepanel;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	private Thread gamethread;
	private AudioOptions audioOptions;
	private GameOptions gameOptions;

	private Playing playing;
	private Menu menu;
	private AudioPlayer audioPlayer;
	
	public final static int TILES_DEFAULT_SIZE = 32; // tile mac dinh de phu hop voi tam hinh vs tung dong tac cua nhan vat (px)
	public final static float SCALE = 1.5f; // thong so dung de phong to tam hinh
	public final static int TILES_IN_WIDTH = 26; // so o vuong theo chieu rong
	public final static int TILES_IN_HEIGHT = 14; // so o vuong theo chieu cao
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE); // kich thuoc cua 1 o vuong sau khi duoc phong to
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH; // chieu dai cua game sau khi duoc phong to 
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT; // chieu cao cua game sau khi duoc phong to
	

	public game() {
		initClasses();
		gamepanel = new gamePanel(this);
		gamewindow = new gameWindow(gamepanel);
		//thiet lap kha nang nhan biet cac su kien tu ban phim
		gamepanel.setFocusable(true);
		// nhan cac su kien truc tiep tu ban phim
		gamepanel.requestFocus();
		
		
		startGameloop();
	}
	
	// bat dau khoi chay thread 
	private void startGameloop() {
		gamethread = new Thread(this);
		gamethread.start();	
	}

	// tao moi obj duoc khai bao o tren
	private void initClasses() {
		audioOptions = new AudioOptions(this);
		audioPlayer = new AudioPlayer();
		gameOptions = new GameOptions(this);
		menu  = new Menu(this);
		playing = new Playing(this);
	}

	
	// uodate cac su kien tuong ung khi chon treb thanh menu
	private void update() {
		
		switch (gameState.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case OPTIONS:
			gameOptions.update();
			break;
		case QUIT:
		default:
			System.exit(0);
			break;
		}
		
	}
	
	// ve lan gamepanel cac thanh phan tuong ung 
	public void render(Graphics g) {
		switch (gameState.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
			break;
		case OPTIONS:
			gameOptions.draw(g);
			break;
		default:
			break;
		}
	}
	
	// khi khoi dong state playing thi nhan vat khong the di chuyen theo huong nao ca
	public void windowfocusLost() {
		if (gameState.state == gameState.PLAYING)
			playing.getPlayer().resetDirBooleans();
	}

	
	// run trong thread
	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		long previousTime = System.nanoTime();
		long lastcheck = System.currentTimeMillis();
		int frame =0;
		int updates =0;
		double deltaU =0;
		double deltaF =0;
	
		// tao 1 vong lap vo han 
	while (true) {
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime -previousTime)/timePerUpdate;
			deltaF += (currentTime -previousTime)/timePerFrame;
			previousTime = currentTime;
			
			if ( deltaU >= 1) {
				update();
				updates++;
				// de tro lai ve 0 hoac gan bang 0
				deltaU--;
			}
			
			if ( deltaF >= 1) {
				// ve lai
				gamepanel.repaint();
				deltaF--;
				frame ++;
			}
	
			if (System.currentTimeMillis() - lastcheck >= 1000) {
				lastcheck=  System.currentTimeMillis();
				System.out.println("FPS: "+ frame + "| UPS: "+ updates);
				updates =0;
				frame =0;
			}
		}	
	}
	
	public Menu getMenu() { 
		return menu;
	}
	
	public Playing getPlaying() {
		return playing;
	}
	
	public AudioOptions getAudioOption() { 
		return audioOptions;
	}
	
	public GameOptions getGameOption() {
		return gameOptions;
	}

	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}	
}


