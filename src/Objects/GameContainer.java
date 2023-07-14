package Objects;
import static ultilz.constants.ObjectConstants.*;

import main.game;

public class GameContainer extends GameObject{

	public GameContainer(int x, int y, int objType) {
		super(x, y, objType);
		createHittBox();
	}

	private void createHittBox() {
		if (objType == BOX) {
			initHitBox(25, 18);
			xDrawOffset = (int)( 7* game.SCALE);
			yDrawOffset = (int)( 12 * game.SCALE);
		} else {
			initHitBox(23, 25);
			xDrawOffset = (int)( 8* game.SCALE);
			yDrawOffset = (int)( 5 * game.SCALE);
		}
		
		hitbox.y += yDrawOffset + (int)(game.SCALE *2);
		hitbox.x += xDrawOffset/2;
		
	}
	
	public void update() {
		if (doAnimation)
			updateAnimationTick();
	}
	
}
