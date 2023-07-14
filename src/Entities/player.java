package Entities;


import static ultilz.constants.PlayerConstants.*;
import static ultilz.constants.*;
import static ultilz.helpMethod.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.xml.xpath.XPath;

import audio.AudioPlayer;
import gameState.Playing;
import main.game;
import ultilz.LoadSave;

public class player extends entity{
	private BufferedImage[][] animation;

	private boolean  moving = false, attacking = false;
	private boolean left, right, jump;
	private int[][] lvData;
	private float xDrawOffSet = 21 * game.SCALE;
	private float yDrawOffset = 4 * game.SCALE;
	
	//jump / gravity
	private float jumpSpeed = -2.25f *game.SCALE;
	private float fallSpeedAfterCollision = 0.05f *game.SCALE;
	
	//STATUS BAR UI
	private BufferedImage statusBarImg;
	
	private int tyleY =0;
	
	private int statusBarWidth = (int) (192 * game.SCALE);
	private int statusBarHeight = (int)(58 * game.SCALE);
	private int statusBarX = (int)(10* game.SCALE);
	private int statusBarY = (int) (10* game.SCALE);
	
	private int healthBarWidth = (int) (150 * game.SCALE);
	private int healthBarHeight = (int)(4 * game.SCALE);
	private int healthBarXStart = (int)( 34* game.SCALE);
	private int healthBarYStart = (int) (14* game.SCALE);
	private int healthWidth = healthBarWidth;
	
	private int powerBarWidth = (int) (104 * game.SCALE);
	private int powerBarHeight = (int) (2 * game.SCALE);
	private int powerBarXStart = (int) (44 * game.SCALE);
	private int powerBarYStart = (int) (34 * game.SCALE);
	private int powerWidth = powerBarWidth;
	private int powerMaxValue = 200;
	private int powerValue = powerMaxValue;

	
	
	private int flipX =0;
	private int flipW =1;
	
	private boolean attackChecked;
	private Playing playing;
	
	private boolean powerAttackActive;
	private int powerAttackTick;
	private int powerGrowSpeed = 15;
	private int powerGrowTick;
	public player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.state = IDLE;
		this.maxHealth =200;
		this.walkSpeed = game.SCALE * 1.0f;
		this.currentHealth = maxHealth;
		this.playing = playing;
		loadAnimation();
		initHitBox(20,26);
		initAttackBox();
	}


	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int)(20* game.SCALE), (int)(20*game.SCALE));
		resetAttackBox();
	}


	public void update() {
		updateHealthBar();
		updatePowerBar();
		
		if (currentHealth <= 0) {
			if (state != DEAD) {
				state =DEAD;
				aniTick=0;
				aniIndex =0;
				playing.setPlayerDying(true);
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
			} else if(aniIndex == GetSpriteAmount(DEAD) -1 && aniTick >= ANI_SPEED -1){
				playing.setGameOver(true);
				playing.getGame().getAudioPlayer().stopSong();
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
			} else {
				updateAniTick();
			}
			return;
		}
		
		updateAttackBox();
		
		updatePos();
		if (moving) {
			checkPotionTounched();
			checkSpikeTounched();
			tyleY =(int) (hitbox.y /game.TILES_SIZE);
			if (powerAttackActive) {
				powerAttackTick++;
				if (powerAttackTick >= 35) {
					powerAttackTick = 0;
					powerAttackActive = false;
				}
			}
		}
		if (attacking || powerAttackActive)
			checkAttack();
		updateAniTick();
		setAnimation();

	}
	
	private void checkSpikeTounched() {
		playing.checkSpikeTounched(this);
		
	}
	
	private void updatePowerBar() {
		powerWidth = (int) ((powerValue / (float) powerMaxValue) * powerBarWidth);

		powerGrowTick++;
		if (powerGrowTick >= powerGrowSpeed) {
			powerGrowTick = 0;
			changePower(1);
		}
	}


	private void checkPotionTounched() {
		playing.checkPotionTounched(hitbox);
		
	}


	private void checkAttack() {
		if (attackChecked || aniIndex != 1)
			return;
		attackChecked = true;

		if (powerAttackActive)
			attackChecked = false;
		
		playing.checkEnemyHit(attackBox);
		playing.checkObjectHit(attackBox);
		playing.getGame().getAudioPlayer().playAttackSound();
	}


	private void updateAttackBox() {
		if (right || (powerAttackActive && flipW == 1))
			attackBox.x = hitbox.x + hitbox.width + (int) (game.SCALE * 10);
		else if (left || (powerAttackActive && flipW == -1))
			attackBox.x = hitbox.x - hitbox.width - (int) (game.SCALE * 10);

		attackBox.y = hitbox.y + (game.SCALE * 10);
		
		
	}


	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth/(float) maxHealth) * healthBarWidth);	
	}


	public void setAttack(boolean attacking) {
		this.attacking = attacking;
	}
	
	private void setAnimation() {
		
		int startAni = state;
		
		if (moving) {
			state = RUNNING;
		}
		else {
			state = IDLE;
		}
		
		// thiet lap chuyen dong  (trang thai cua nhan vat tuong ung voi tung hanh dong )
		if (inAir) {
			if (airSpeed < 0) {
				state = JUMP;	
			}else
				state = FALING;
		}
		if (powerAttackActive) {
			state = ATTACK;
			aniIndex =1;
			aniTick =0;
			return;
		}
		
		if (attacking) {
			state = ATTACK;
			if (startAni != ATTACK) {
				aniIndex =1;
				aniTick =0;
				return;
				
			}
		}
		
		if (startAni != state) {
			resetAniTick();
		}
		
	}

	private void resetAniTick() {
		aniTick =0;
		aniIndex =0;
		
	}

	private void updateAniTick() {
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick =0;
			aniIndex ++;
			if (aniIndex >= GetSpriteAmount(state)) {
				aniIndex=0;
				attacking= false;
				attackChecked = false;
			}
			
		}
		
	}
	
	private void updatePos() {
		moving = false;
		
		if (jump)
			jump();
		
		if (!inAir) {
			if (!powerAttackActive)
				if ((!left && !right) || (right && left))
					return;
		}
		
		float xSpeed =0;
		
		if ( left && !right) {
			xSpeed -= walkSpeed;
			flipX = width;
			flipW = -1;
		}
			
		if (right && !left) {
			xSpeed += walkSpeed;
			flipX=0;
			flipW=1;
		}
		
		if (powerAttackActive) {
			if ((!left && !right) ||  (left && right)) {
				if (flipW == -1)
					xSpeed = -walkSpeed;
				else
					xSpeed = walkSpeed;	
			}
			xSpeed *=3;
			
		}
			
		
		if (!inAir) {
			if (!isEntityOnFloor (hitbox, lvData)) {
				inAir = true;
			}
		}
		
		if (inAir && !powerAttackActive) {
			if ( canMoveHere(hitbox.x, hitbox.y +airSpeed, hitbox.width, hitbox.height, lvData)) {
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
				updateXPos(xSpeed);
			}else {
				hitbox.y = GetEnityPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
		}else {
			updateXPos(xSpeed);
		}	
		moving = true;	
	}
	
	// thay doi health cua player
	public void changeHealth(int value) {
		currentHealth += value;
		if (currentHealth <=0) {
			currentHealth= 0;
			//game over
		}
		else if (currentHealth >= maxHealth) {
			currentHealth = maxHealth;
		}	
	}

	
	// tao dong tac nhay len cho nhan vat
	private void jump() {
		if (inAir)
			return;
		// am thanh hieu ung nhay sẽ bat đau khoi dong
		playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
		// thiet lap dang o trang thai tren khong
		inAir = true;
		airSpeed = jumpSpeed;
		
	}

	// thiet lap lai trang thai truoc khi nhay
	private void resetInAir() {
		inAir = false;
		airSpeed =0;
	}


	private void updateXPos(float xSpeed) {
		
		if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvData)) {
			hitbox.x += xSpeed;
		}else {
			hitbox.x = GetEnityPosNextToWall(hitbox, xSpeed);
			if (powerAttackActive) {
				powerAttackActive = false;
				powerAttackTick = 0;
			}
		}
		
	}


	public void render(Graphics g, int lvOffset) { 
		g.drawImage(animation[state][aniIndex],
				(int)(hitbox.x -xDrawOffSet -lvOffset) +flipX,
				(int) (hitbox.y -yDrawOffset), 
				width * flipW, height, null );
		drawUI(g);
	}

	private void drawUI(Graphics g) {
		//background ui
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		// health ui
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
		
		//power bar
		g.setColor(Color.yellow);
		g.fillRect(powerBarXStart + statusBarX, powerBarYStart + statusBarY, powerWidth, powerBarHeight);

	}


	private void loadAnimation() {
		
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		
		animation = new BufferedImage[7][8];
			
		for (int j=0; j< animation.length; j++) {
			for (int i=0; i< animation[j].length; i++) {
				animation[j][i] = img.getSubimage(i*64, j*40 , 64, 40);
			}
		}
	
		statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
	}
	
	public void loadLvData(int[][] lvData) {
		this.lvData = lvData;
		if (!isEntityOnFloor(hitbox, lvData)) {
			inAir = true;
		}
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}


	public void setRight(boolean right) {
		this.right = right;
	}


	public void setJump (boolean jump) {
		this.jump = jump;
	}

	
	// thiet lap player khong the di chuyen theo huong trai phai
	public void resetDirBooleans() {
		left = false;
		right = false;
	}
	
	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y=spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}


	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking= false;
		airSpeed = 0f;
		moving= false;
		state = IDLE;
		currentHealth = maxHealth;
		
		powerAttackActive = false;
		powerAttackTick = 0;
		powerValue = powerMaxValue;
		
		hitbox.x = x;
		hitbox.y = y;
		resetAttackBox();
		
		if (!isEntityOnFloor(hitbox, lvData)) {
			inAir = true;
		}
		
	}
	
	private void resetAttackBox() {
		if (flipW ==1) {
			attackBox.x = hitbox.x + hitbox.width + (int) (game.SCALE *10);	
		}
		else {
			attackBox.x = hitbox.x - hitbox.width - (int) (game.SCALE *10);
		}
	}


	public void changePower(int value) {
		powerValue += value;
		if (powerValue >= powerMaxValue)
			powerValue = powerMaxValue;
		else if (powerValue <= 0)
			powerValue = 0;
	}


	public void kill() {
		currentHealth =0;
		
	}


	public int getTyleY() {
		return tyleY;
	}


	public void powerAttack() {
		if (powerAttackActive)
			return;
		if (powerValue >= 60) {
			powerAttackActive = true;
			changePower(-60);
		}
		
	}

	

}
