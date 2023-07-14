package Entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameState.Playing;
import levels.level;
import ultilz.LoadSave;
import static ultilz.constants.EnemyConstants.*;

public class EnemyManager {
	private Playing playing;
	private BufferedImage[][] crabbyArr;
	private ArrayList<Crabby> crabbies = new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing =playing;
		loadEnemyImg();
	}
	
	
	public void loadEnemies(level Level) {
		crabbies = Level.getCrabs();
	}
	
	

	public  void update(int[][] lvData, player Player) {
		boolean isAnyActive = false;
		for (Crabby c : crabbies) {
			if (c.isActive()) {
				c.update(lvData, Player);
				isAnyActive = true;
			}	
		}
		if (! isAnyActive)
			playing.setLevelComplete(true);
	}
	
	public void draw(Graphics g, int xLvOffset) {
		drawGrabs(g , xLvOffset);
	}

	private void drawGrabs(Graphics g, int xLvOffset) {
		for (Crabby c : crabbies) {
			if (c.isActive()) {
				g.drawImage(crabbyArr[c.getEnemyState()][c.getAniIndex()], 
						(int) c.getHitbox().x - xLvOffset - CRABBY_DRAWOFFSET_X  + c.flipX(), 
						(int) c.getHitbox().y - CRABBY_DRAWOFFSET_Y, 
						CRABBY_WIDTH * c.flipW(),
						CRABBY_HEIGHT, null);
//				c.drawAttackBox(g, xLvOffset);
			}
		}		
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Crabby c : crabbies) {
			if (c.isActive()) {
				if (c.currentHealth > 0)
					if (attackBox.intersects(c.getHitbox())) {
						c.hurt(10);
						return ;
					}
			}
		
		}
	}
	

	private void loadEnemyImg() {
		crabbyArr = new BufferedImage[5][9];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.GRABBY_SPRITE);
		for (int j =0; j< crabbyArr.length; j++) {
			for (int i=0; i< crabbyArr[j].length; i++) {
				crabbyArr[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFALUT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFALUT);
			}
		}
		
	}

	public void resetAllEnemies() {
		for (Crabby c : crabbies)
			c.resetEnemy();
		
	}
}
