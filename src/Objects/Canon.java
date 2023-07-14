package Objects;

import main.game;

public class Canon extends GameObject{
	private int tyleY ;


	public Canon(int x, int y, int objType) {
		super(x, y, objType);
		tyleY = y/game.TILES_SIZE;
		initHitBox(40, 26);
		hitbox.x -= (int)(4* game.SCALE);
		hitbox.y += (int)(6* game.SCALE);
	}
	
	public void update() {
		if (doAnimation)
			updateAnimationTick();
	}

	public int getTyleY() {
		return tyleY;
	}
	
	


	

}
