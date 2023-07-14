package Entities;

import static ultilz.constants.EnemyConstants.*;
import static ultilz.constants.Direction.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import main.game;

public class Crabby extends Enemy{
	private int attackBoxOffsetX;
	
	public Crabby(float x, float y) {
		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
		// thiet lap box cho crabby vs rong  va cao
		initHitBox(22 ,19 );
		initAttackBox();
	}
	
	// tao moi cac attack box cho crabby 
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int)(82 * game.SCALE), (int) (19* game.SCALE));
		// xOffset duoc dich chuyen sang ben phai so crabby
		attackBoxOffsetX = (int)(game.SCALE * 30);
	}
	

	public void update(int[][] lvData, player Player) {
		updateBehavior(lvData , Player);
		updateAnimationTick();
		updateAttackBox();
	}

 
	private void updateAttackBox() {
		attackBox.x = hitbox.x - attackBoxOffsetX;
		attackBox.y = hitbox.y;	
	}

	private void updateBehavior(int[][] lvData, player Player) {
		if (fristUpdate) 
			firstUpdateChheck(lvData);
		if (inAir) 
			updateInAir(lvData);
		else {
			switch(state) {
			case IDLE:
				newState(RUNNING);
				break;
			case RUNNING:
				if (canSeePlayer(lvData, Player)) {
					turnTowardsPlayer(Player);
					if (isPlayerCloseForAttack(Player))
						newState(ATTACK);
				}
				move(lvData);
				break;
			case ATTACK:
				
				if (aniIndex ==0)
					attackChecked= false;
				
				if (aniIndex ==3 && !attackChecked)
					checkPlayerHit(attackBox, Player);
				break;
			case HIT:
				break;
			}
		}
	}
	

	public int flipX() {
		if (walkDir == RIGHT)
			return width;
		else 
			return 0;
	}
	
	public int flipW() {
		if (walkDir == RIGHT)
			return -1;
		else 
			return 1;
	}


}
