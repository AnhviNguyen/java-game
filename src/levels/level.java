package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Entities.Crabby;
import Objects.Canon;
import Objects.GameContainer;
import Objects.Potion;
import Objects.Spike;
import main.game;
import ultilz.helpMethod;

import static ultilz.helpMethod.GetLevelData;
import static ultilz.helpMethod.GetCrabs;
import static ultilz.helpMethod.GetPlayerSpawn;;

public class level {
	private int[][] lvData;
	private BufferedImage img;
	private ArrayList<Crabby> crabs;
	private ArrayList<Potion> potions;
	private ArrayList<Spike> spikes;
	private ArrayList<Canon> canons;
	private ArrayList<GameContainer> containers;
	private int lvTilesWide ;
	private int maxTilesOffset;
	private int maxLvOffset;
	private Point lvSpawn;
	
	public level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		createContainers();
		createSpikes();
		createPotions();
		createCanons();
		
		calcLvOffset();
		calPlayerSpawn();
	}
	
	private void createCanons() {
		canons = helpMethod.GetCanons(img);
		
	}

	private void createSpikes() {
		spikes = helpMethod.GetSpikes(img);
		
	}

	private void createPotions() {
		potions = helpMethod.GetPotions(img);
	}

	private void createContainers() {
		containers = helpMethod.GetContainers(img);
		
	}

	private void calPlayerSpawn() {
		lvSpawn = GetPlayerSpawn(img);
		
	}

	public Point getLvSpawn() {
		return lvSpawn;
	}

	public int getLvOffset() {
		return maxLvOffset;
	}
	
	public int getSpriteIndex(int x, int y) {
		return lvData[y][x];
	}
	
	public ArrayList<Crabby> getCrabs(){
		return crabs;
	}
	// tinh toan khoang cach de no bi xoa khi ra khoi duong vien
	private void calcLvOffset() {
		lvTilesWide = img.getWidth();
		maxTilesOffset = lvTilesWide - game.TILES_IN_WIDTH;
		maxLvOffset = game.TILES_SIZE * maxTilesOffset; 
	}

	private void createEnemies() {
		crabs = GetCrabs(img);
	}

	private void createLevelData() {
		lvData = GetLevelData(img);
	}

	public int[][] getLevelData() {
		return lvData;
	}

	public ArrayList<Potion> getPotions() {
		return potions;
	}

	public ArrayList<GameContainer> getContainers() {
		return containers;
	}

	public ArrayList<Spike> getSpikes() {
		return spikes;
	}

	public ArrayList<Canon> getCanons() {
		return canons;
	}

	
}
