package Objects;

import main.game;

public class Potion extends GameObject{
	
	private float hoverOffset;
	private int maxHoverOffset, hoverDir =1;
	

	public Potion(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation = true;
		initHitBox(7, 17);
		xDrawOffset = (int)(3* game.SCALE);
		yDrawOffset = (int)(2* game.SCALE);
		
		maxHoverOffset = (int)(10* game.SCALE);
		
	}
	
	public void update() {
		updateAnimationTick();
		updateHover();
	}

	private void updateHover() {
		hoverOffset += (0.075f *game.SCALE *hoverDir);
		if (hoverOffset >= maxHoverOffset) {
			hoverDir = -1;
		}
		else if (hoverOffset < 0) {
			hoverDir =1;
		}
		
		hitbox.y = y + hoverOffset;
		
	}
}
