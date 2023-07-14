package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.game;

public abstract class entity {

	protected float x, y;
	protected int width, height;
	protected int aniTick, aniIndex;
	protected int state;
	protected float airSpeed;
	protected boolean inAir = false;
	protected int maxHealth;
	protected int currentHealth;
	protected float walkSpeed = 1.0f * game.SCALE;
	
	protected Rectangle2D.Float attackBox;
	protected Rectangle2D.Float hitbox;
	
	// contructor cua enity
	public entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	
	// xLvOffset: gia tri cua nhan vat sau khi thay doi vi tri moi ( x- newX)
	protected void drawHitbox(Graphics g, int xLvOffset) {
		// for debudding the hitbox
		g.setColor(Color.PINK);
		g.drawRect((int)hitbox.x - xLvOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
		
	}
	
	
	// ve box cho vu khi 
	protected void drawAttackBox(Graphics g, int xLvOffset) {
		g.setColor(Color.red);
		g.drawRect((int)(attackBox.x -xLvOffset) ,(int)attackBox.y,(int) attackBox.width,(int) attackBox.height);
	}
	
	// tao moi mot hitbox (cho nhan vat) 
	protected void initHitBox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int)(width * game.SCALE),(int)( height * game.SCALE));
	}
	
	public  Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	
	public int getEnemyState() {
		return state;
	}
	
	public int getAniIndex() {
		return aniIndex;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}
}
