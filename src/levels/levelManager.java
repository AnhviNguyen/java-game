package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameState.gameState;
import main.game;
import ultilz.LoadSave;

public class levelManager {
	private game Game;
	private BufferedImage[] levelSprite;
	private ArrayList<level> levels;
	private int lvIndex =0;
	
	public levelManager(game game) {
		this.Game = game;
		importOutsideSprites();
		levels = new ArrayList<>();
		buildAllLevels();
	}
	// luu tru cac level img trong 
	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.getAllLevels();
		for (BufferedImage img: allLevels) {
			levels.add(new level(img));
		}
		
	}
// tai anh cay hoa dat thanh array
	private void importOutsideSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	// vẽ các sprite tại các vị trí tương ứng
	public void draw(Graphics g, int lvOffset) {
		for (int j = 0; j < game.TILES_IN_HEIGHT; j++) 
			
			for (int i = 0; i < levels.get(lvIndex).getLevelData()[0].length; i++) {
				int index = levels.get(lvIndex).getSpriteIndex(i, j);  //?
				g.drawImage(levelSprite[index], game.TILES_SIZE * i -lvOffset, game.TILES_SIZE * j, game.TILES_SIZE, game.TILES_SIZE, null);
			}
		}

	public void update() {
		
		
	}
	
	// load next level 
	public void loadNextLevel() {
		lvIndex++;
		if (lvIndex >= levels.size()) {
			lvIndex =0;
			System.out.println("no more levels game");
			gameState.state = gameState.MENU;
		}
		level newLevel = levels.get(lvIndex);
		Game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		Game.getPlaying().getPlayer().loadLvData(newLevel.getLevelData());
		Game.getPlaying().setMaxLvOffset(newLevel.getLvOffset());
		Game.getPlaying().getObjectManager().loadObjects(newLevel);
	}
	
	public level getCurrentLevel() {
		return levels.get(lvIndex);
	}
	
	public int getAmountOfLevels() {
		return levels.size();
	}

	public int getLvIndex() {
		return lvIndex;
	}	
}
