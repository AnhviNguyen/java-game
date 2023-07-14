package Objects;

import static ultilz.constants.ANI_SPEED;

import static ultilz.constants.ObjectConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.game;

public class GameObject {
	protected int x, y, objType;
	protected Rectangle2D.Float hitbox;
	protected boolean doAnimation, active = true;
	protected int aniTick, aniIndex;
	protected int xDrawOffset, yDrawOffset;
	
	public GameObject(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}
	
	protected void initHitBox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int)(width * game.SCALE),(int)( height * game.SCALE));
	}
	
	public void drawHitbox(Graphics g, int xLvOffset) {
		g.setColor(Color.PINK);
		g.drawRect((int)hitbox.x - xLvOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
		
	}
	
	protected void updateAnimationTick() {
		aniTick ++;
		if (aniTick >= ANI_SPEED) {
			aniTick =0;
			aniIndex++;
			if (aniIndex >=GetSpriteAmount(objType)) {
				aniIndex =0;
				if (objType == BARREL || objType == BOX) {
					doAnimation = false;
					active = false;
				}
				else if (objType == CANON_RIGHT || objType == CANON_LEFT) {
					doAnimation = false;
				}
				
			}	
		}
	}
	
	

	public int getAniIndex() {
		return aniIndex;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void reset() {
		aniIndex =0;
		aniTick =0;
		active= true;
		if ( objType == BARREL || objType == BOX || objType == CANON_LEFT || objType == CANON_RIGHT)
			doAnimation = false;
		else
			doAnimation = true;
	}

	public int getObjType() {
		return objType;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public boolean isActive() {
		return active;
	}

	public int getxDrawOffset() {
		return xDrawOffset;
	}

	public int getyDrawOffset() {
		return yDrawOffset;
	}

	public void setAnimation(boolean doAnimation) {
		this.doAnimation = doAnimation;
	}

	public int getAniTick() {
		return aniTick;
	}	
}
