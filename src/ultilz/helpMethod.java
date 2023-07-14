package ultilz;

import static ultilz.constants.EnemyConstants.CRABBY;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static ultilz.constants.ObjectConstants.*;
import Entities.Crabby;
import Objects.Canon;
import Objects.GameContainer;
import Objects.Potion;
import Objects.Projectile;
import Objects.Spike;
import main.game;

public class helpMethod {
	
	// kiem tra xem vi tri do co bi chsn hay khong
	private static boolean isSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * game.TILES_SIZE;
		
		if ( x < 0 || x >= maxWidth) {
			return true;
		}
		if ( y< 0 || y >= game.GAME_HEIGHT) {
			return true;
		}
		
		float xIndex = x/ game.TILES_SIZE;
		float yIndex = y/ game.TILES_SIZE;
		return isTileSolid((int)xIndex, (int) yIndex, lvlData);
	}
	
	// kiem tra xem 1 o vuong cu the co phai la khoi chan hay khong
	public static boolean isTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[(int) yTile][(int )xTile];
		if (value >= 48 || value < 0 || value != 11) {
			return true;
		}
		return false;
	}
	
	
	// kiem tra xem  vung (o) co the di chuyen duoc hay khong
	public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!isSolid(x, y, lvlData)) {
			if (!isSolid(x + width, y + height, lvlData)){
				if (!isSolid(x+ width, y, lvlData)) {
					if (!isSolid(x, y+height, lvlData)) {
						return true;
					}
				}
			}
		}
		return false;	
	}
	
	public static float GetEnityPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int)(hitbox.x / game.TILES_SIZE);
		if (xSpeed > 0) {
			//right
			int tilesPos = currentTile * game.TILES_SIZE;
			int xOffset = (int)(game.TILES_SIZE - hitbox.width);
			return tilesPos + xOffset -1;
		}else {
			//left
			return currentTile * game.TILES_SIZE;
		}
	}
	
	public static float GetEnityPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int)(hitbox.y / game.TILES_SIZE);
		if (airSpeed > 0) {
			//falling - touching floor
			int tileYPos = currentTile * game.TILES_SIZE;
			int yOffset = (int)(game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset -1;
		}else {
			//jumping
			return currentTile * game.TILES_SIZE;
		} 
	}
	
	// kiem tra xem khoi do co duoc nam tren san hay khong
	public static boolean isEntityOnFloor(Rectangle2D.Float hitbox,int[][] lvlData ) {
		if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;
		return true;
	}
	
	
	public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		if (xSpeed >0)
			return isSolid(hitbox.x +hitbox.width +xSpeed, hitbox.y +hitbox.height + 1, lvlData);
		else
			return isSolid(hitbox.x + xSpeed, hitbox.y +hitbox.height + 1, lvlData);
	}
	
	
	
	public static boolean isSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox,Rectangle2D.Float secondHitbox, int tyleY ) {
		int firstXTyile = (int)(firstHitbox.x /game.TILES_SIZE);
		int secondXTyile = (int)(secondHitbox.x /game.TILES_SIZE);
		
		if (firstXTyile > secondXTyile) {
			return isAllTileWalkable(secondXTyile, firstXTyile, tyleY, lvlData);
		}else {
			return isAllTileWalkable(firstXTyile, secondXTyile, tyleY, lvlData);
		}
	}
	
	
	public static boolean isAllTileWalkable(int xStart, int xEnd, int y, int[][] lvData) {
		if (IsAllTilesClear(xStart, xEnd, y, lvData))
			for (int i=0; i< xEnd - xStart; i++) {
				if (isTileSolid(xStart+ i, y+ 1, lvData))
					return false;
			}
		return true;
	}
	
	// sap xep nui non do theo thu tu cua tung px
	public static int[][] GetLevelData(BufferedImage img){	
		
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];

		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48)
					// de khong ve cho px do
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;
	}
	
	// lay grab o vi tri cu the ( thoe tung pixel) trong img
	public static ArrayList<Crabby> GetCrabs(BufferedImage img){
		ArrayList<Crabby> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == CRABBY)
					list.add(new Crabby(i * game.TILES_SIZE, j * game.TILES_SIZE));
			}
		return list;
	}
	
	public static Point GetPlayerSpawn(BufferedImage img) {
		for (int j=0; j< img.getHeight(); j++) {
			for (int i=0; i< img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == 100)
					return new Point(i* game.TILES_SIZE, j* game.TILES_SIZE);
			}
		}
		return new Point(1* game.TILES_SIZE, 1* game.TILES_SIZE);
	}
	
	public static ArrayList<Potion> GetPotions(BufferedImage img){
		ArrayList<Potion> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == RED_POTION || value == BLUE_POTION)
					list.add(new Potion(i * game.TILES_SIZE, j * game.TILES_SIZE, value));		
			}
		return list;
	}
	// tai nhung diem mau tuong ung ve nhung thung hang co chua vat pham
	public static ArrayList<GameContainer> GetContainers(BufferedImage img){
		ArrayList<GameContainer> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == BOX || value == BARREL)
					list.add(new GameContainer(i * game.TILES_SIZE, j * game.TILES_SIZE, value));		
			}
		return list;
	}
	// 
	public static ArrayList<Spike> GetSpikes(BufferedImage img) {
		ArrayList<Spike> list = new ArrayList<>();
		
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
//				if (value == SPIKE)
//					list.add(new Spike(i * game.TILES_SIZE, j * game.TILES_SIZE, SPIKE));		
			}
		return list;
	}
	
	// tai nhung diem mau tuong ung ve cannon ( dai bac) o vi tri do
	public static ArrayList<Canon> GetCanons(BufferedImage img) {
		ArrayList<Canon> list = new ArrayList<>();
		
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == CANON_LEFT || value == CANON_RIGHT)
					list.add(new Canon(i * game.TILES_SIZE, j * game.TILES_SIZE, value));		
			}
		return list;
	}
	
	
	public static boolean canCanonSeePlayer(int[][] lvlData, Rectangle2D.Float firstHitbox,Rectangle2D.Float secondHitbox, int tyleY) {
		int firstXTyile = (int)(firstHitbox.x /game.TILES_SIZE);
		int secondXTyile = (int)(secondHitbox.x /game.TILES_SIZE);
		
		if (firstXTyile > secondXTyile) {
			return IsAllTilesClear(secondXTyile, firstXTyile, tyleY, lvlData);
		}else {
			return IsAllTilesClear(firstXTyile, secondXTyile, tyleY, lvlData);
		}
	}
	
	public static boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] lvData) {
		for (int i=0; i< xEnd - xStart; i++) {
			if (isTileSolid(xStart+ i, y, lvData)) {
				return false;
			}
		}
		return true;
			
	}
	
	public static boolean IsProjectileHittingLevel(Projectile p, int[][] lvData) {
		return  isSolid(p.getHitbox().x + p.getHitbox().width /2, p.getHitbox().y + p.getHitbox().height/2, lvData);
	}
	
}


