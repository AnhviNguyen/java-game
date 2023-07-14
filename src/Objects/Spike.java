package Objects;

import main.game;

public class Spike extends GameObject{

	public Spike(int x, int y, int objType) {
		super(x, y, objType);
		
		initHitBox(32, 16);
		xDrawOffset=0;
		yDrawOffset = (int) (game.SCALE *16);
		hitbox.y += yDrawOffset;
	}
	
}
