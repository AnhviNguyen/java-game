package Entities;
import static ultilz.constants.EnemyConstants.*;
import static ultilz.constants.*;
import static ultilz.helpMethod.*;

import java.awt.geom.Rectangle2D;


import static ultilz.constants.Direction.*;

import main.game;

public abstract class Enemy extends entity{
	protected int  enemyType;
	protected boolean fristUpdate = true;

	protected float  walkSpeed = 0.35f * game.SCALE;
	protected int walkDir = LEFT;
	protected int tyleY;
	protected float attackDistance = game.TILES_SIZE;
	protected boolean active = true;
	protected boolean attackChecked;
	
	// contructor cua enemy	 (thiet lap cac gia tri ban dau cho enemy)
	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType =enemyType;
		this.walkSpeed = game.SCALE * 0.35f;
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
	}
	
	// doi huong cho player
	protected void turnTowardsPlayer(player Player) {
		if (Player.hitbox.x > hitbox.x) {
			walkDir = RIGHT;
		}else {
			walkDir = LEFT;
		}
	}
	
	
	// tao hieu ung cuyen dong cho nhan vat 
	protected void updateAnimationTick() {
		aniTick ++;
		if (aniTick >= ANI_SPEED) {
			//moi chuyen dong se tuong ung vs 1 ANI_SPEED, neu lon hon no se tu dong chuyen sang hinh tiep theo
			aniTick =0;
			aniIndex++;
			if (aniIndex >=GetSpriteAmount(enemyType, state)) {
				aniIndex =0;
				switch(state) {
				// neu trong state nhu duoi thi se co nhung hanh dong tuong ung
				case ATTACK, HIT -> state = IDLE;
				case DEAD -> active = false;
				}	
			}
		}
	}
	
	
	// 
	protected void firstUpdateChheck(int[][] lvData) {
		if (!isEntityOnFloor(hitbox, lvData)) {
			inAir = true;
		}
		fristUpdate = false;
	}
	
	// 
	protected void updateInAir(int[][] lvData) {
		if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvData)) {
			hitbox.y += airSpeed;
			airSpeed += GRAVITY;
		}
		else {
			inAir = false;
			hitbox.y = GetEnityPosUnderRoofOrAboveFloor(hitbox, airSpeed);
			tyleY = (int) (hitbox.y / game.TILES_SIZE);
			
		}	
	}
	
	protected void move(int[][] lvData) {
		float xSpeed =0;
		
		if (walkDir == LEFT)
			xSpeed = -walkSpeed; 
		else
			xSpeed = walkSpeed;
		if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvData))
			if (isFloor(hitbox, xSpeed, lvData)) {
				hitbox.x += xSpeed;
				return;
			}
		changeWalkDir();
	}
	
	protected void newState(int enemyStatte) {
		this.state = enemyStatte;
		aniTick=0;
		aniIndex=0;
	}

	protected boolean canSeePlayer(int[][] lvData, player Player) {
		int playerTileY =(int) (Player.getHitbox().y / game.TILES_SIZE);
		if (playerTileY == tyleY)
			if (isPlayerInRange(Player)) {
				if (isSightClear(lvData, hitbox, Player.hitbox, tyleY)) {
					return true;
				}
			}
		return false;	
	}

	
	protected boolean isPlayerInRange(player player) {
		int absValue = Math.abs((int)(player.hitbox.x - hitbox.x));
		return absValue <= attackDistance *5;
	}

	
	
	protected boolean isPlayerCloseForAttack(player Player) {
		int absValue = Math.abs((int)(Player.hitbox.x - hitbox.x));
		return absValue <= attackDistance;
	}
	
	// thay doi huong di
	protected void changeWalkDir() {
		if (walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}
	
	//  kiem tra xem hai hinh chu nhat co giao nhau( attack cua doi phuong cat nhau) 
	protected void checkPlayerHit(Rectangle2D.Float attackBox ,player player) {
		if (attackBox.intersects(player.hitbox))
			// plyayer sẽ thay doi health
			player.changeHealth(-getEnemyDmg(enemyType));
		attackChecked = true;	
	}

	// khi bi player tan cong 
	public void hurt(int amount) {
		currentHealth -= amount;
		if (currentHealth <=0) {
			newState(DEAD);
		}else {
			newState(HIT);
		}
	}
	
	//
	public boolean isActive() {
		return active;
	}
	
	
	// enemy se duoc reset lai tu dau
	public void resetEnemy() {
		hitbox.x =x;
		hitbox.y= y;
		fristUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		airSpeed =0;
	}

}
