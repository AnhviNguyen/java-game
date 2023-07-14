package gameState;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.util.Random;

import Entities.EnemyManager;
import Entities.player;
import Objects.ObjectManager;
import levels.level;
import levels.levelManager;
import main.game;
import ui.GameOverOverlay;
import ui.LevelCompleteOverlay;
import ui.PauseOverlay;
import ultilz.LoadSave;
import static ultilz.constants.Evironment.*;

public class Playing extends state implements stateMethod{
	private player Player;
	private levelManager levelmanager;
	private PauseOverlay pauseOverlay;
	private boolean paused = false;
	private EnemyManager enemyManager;
	private ObjectManager objectManager;
	
	private GameOverOverlay gameoverlay;
	private LevelCompleteOverlay levelCompleteOverlay;
	
	private int xLvOffset ;
	private int leftBorder = (int) (0.2 * game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * game.GAME_WIDTH);
	private int maxLvOffset;
//	
	private BufferedImage backgroundImg, bigCloud, smallCloud;
	private int[] smallCloudPos;
	private Random rd = new Random();
	
	private boolean gameOver;
	private boolean lvCompleted;
	private boolean playerDying;
	
	public Playing(game Game) {
		super(Game);
		initClasses();	
		
		backgroundImg  = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
		bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUD);
		smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUD);
		smallCloudPos = new int[8];
		for (int i=0; i< smallCloudPos.length; i++) {
			smallCloudPos[i] = (int)(rd.nextInt((int)(game.SCALE * 100)) + 90 * game.SCALE);
		}
		
		calLvOffset();
		loadStartLevel();
	}
 
	public void loadNextLevel() {
		levelmanager.loadNextLevel();
		Player.setSpawn(levelmanager.getCurrentLevel().getLvSpawn());
		resetAll();
	}
	
	private void loadStartLevel() {
		enemyManager.loadEnemies(levelmanager.getCurrentLevel());
		objectManager.loadObjects(levelmanager.getCurrentLevel());
	}



	private void calLvOffset() {
		maxLvOffset = levelmanager.getCurrentLevel().getLvOffset();
	}
	
	public void setMaxLvOffset(int lvOffset) {
		this.maxLvOffset = lvOffset;
	}

	private void initClasses() {
		levelmanager = new levelManager(Game);
		enemyManager = new EnemyManager(this);
		objectManager = new ObjectManager(this);
		
		Player = new player(200, 200, (int) (64 * game.SCALE), (int) (40 * game.SCALE),this);
		Player.loadLvData(levelmanager.getCurrentLevel().getLevelData());
		Player.setSpawn(levelmanager.getCurrentLevel().getLvSpawn());
		pauseOverlay = new PauseOverlay(this);
		gameoverlay = new GameOverOverlay(this);
		levelCompleteOverlay = new LevelCompleteOverlay(this);
	}
	
	public ObjectManager getObjectManager() {
		return objectManager;
	}


	public void windowfocusLost() {
		Player.resetDirBooleans();
	}
	
	public player getPlayer() {
		return Player;
	}



	@Override
	public void update() {
		if (paused) {
			pauseOverlay.update();
		} else if (lvCompleted) {
			levelCompleteOverlay.update();
		} else if (gameOver) {
			gameoverlay.update();
		} else if (playerDying) {
			Player.update();
		} else {
			levelmanager.update();
			objectManager.update(levelmanager.getCurrentLevel().getLevelData(), Player);
			Player.update();
			enemyManager.update(levelmanager.getCurrentLevel().getLevelData(), Player);
			checkCloseToBorder();
		}
	}


	private void checkCloseToBorder() {
		int playerX = (int) Player.getHitbox().getX();
		int diff = playerX - xLvOffset;
		
		if ( diff > rightBorder)
			xLvOffset += diff - rightBorder;
		else if (diff <leftBorder)
			xLvOffset += diff -leftBorder;	
		
		if (xLvOffset > maxLvOffset)
			xLvOffset=maxLvOffset;
		else if (xLvOffset < 0)
			xLvOffset =0;
	}



	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, game.GAME_WIDTH, game.GAME_HEIGHT, null);
		drawCloud(g);
		
		levelmanager.draw(g, xLvOffset);
		Player.render(g, xLvOffset);
		enemyManager.draw(g, xLvOffset);
		objectManager.draw(g, xLvOffset);
		
		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, game.GAME_WIDTH, game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}else if (gameOver) {
			gameoverlay.draw(g);
		}
		else if (lvCompleted)
			levelCompleteOverlay.draw(g);
		
	
	}
	
	public void setLevelComplete(boolean levelComplete) {
		this.lvCompleted = levelComplete;
		if (levelComplete) {
			Game.getAudioPlayer().lvComplete();
		}
	}



	private void drawCloud(Graphics g) {
		for (int i=0; i< 3; i++)
			g.drawImage(bigCloud,i * BIG_CLOUD_WIDTH - (int)(xLvOffset * 0.3),(int) ( 204* game.SCALE),  BIG_CLOUD_WIDTH,BIG_CLOUD_HEIGHT, null);
		
		for (int i=0; i< smallCloudPos.length; i++)
			g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int)(xLvOffset * 0.8), smallCloudPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT,  null);
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameOver) {
			if (e.getButton() == MouseEvent.BUTTON1)
				Player.setAttack(true);
			else if (e.getButton() == MouseEvent.BUTTON3)
				Player.powerAttack();
		}
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mousePressed(e);
			else if (lvCompleted)
				levelCompleteOverlay.mousePressed(e);
		}else {
			gameoverlay.mousePressed(e);
		}
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mouseReleased(e);
			else if (lvCompleted)
				levelCompleteOverlay.mouseReleased(e);
		}else {
			gameoverlay.mouseReleased(e);
		}
	}
	
	



	@Override
	public void mouseMoved(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mouseMoved(e);
			else if (lvCompleted)
				levelCompleteOverlay.mouseMoved(e);
		}else {
			gameoverlay.mouseMoved(e);
		}
		
	}
	
	public void unpausedGame() {
		paused = false;
	}

	
	public void mouseDragged(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverlay.mouseDragged(e);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if (gameOver)
			gameoverlay.keyPressed(e);
		else 
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				Player.setLeft(true);
				break;
			case KeyEvent.VK_D:
				Player.setRight(true);
				break;
			case KeyEvent.VK_SPACE:
				Player.setJump(true);
				break;
			case KeyEvent.VK_BACK_SPACE:
				gameState.state = gameState.MENU;
				break;
			case KeyEvent.VK_ESCAPE:
				paused = ! paused;
				break;
			}		
	}



	@Override
	public void keyRealeased(KeyEvent e) {
		if (!gameOver)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				Player.setLeft(false);
				break;
			case KeyEvent.VK_D:
				Player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				Player.setJump(false);
				break;
			}
	}
	
	public void resetAll() {
		gameOver = false;
		paused= false;
		lvCompleted = false;
		playerDying= false;
		Player.resetAll();
		enemyManager.resetAllEnemies();
		objectManager.resetAllObject();
		
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}
	
	public void setGameOver (boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	public void checkPotionTounched(Rectangle2D.Float hitbox) {
		objectManager.checkObjectTounched(hitbox);
		
	}

	public void checkObjectHit(Rectangle2D.Float  attackBox) {
		objectManager.checkObjectHit(attackBox);
		
	}

	public levelManager getLevelManager() {
		return levelmanager;
	}

	public void checkSpikeTounched(player p) {
		objectManager.checkSpikesTounched(p);
		
	}

	public void setPlayerDying(boolean playerDying) {
		this.playerDying= playerDying;		
	}
}
