package Objects;

import java.awt.Graphics;
import static ultilz.helpMethod.canCanonSeePlayer;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Entities.player;
import gameState.Playing;
import levels.level;
import main.game;
import ultilz.LoadSave;
import static ultilz.constants.ObjectConstants.*;
import static ultilz.constants.Projecttiles.*;
import static ultilz.helpMethod.IsProjectileHittingLevel;

public class ObjectManager {
	private Playing playing;
	private BufferedImage[][] potionImg, containerImg;
	private BufferedImage spikeImg, canonBallImg;
	private BufferedImage[] canonImg;;
	private ArrayList<Potion> potions;
	private ArrayList<GameContainer>  containers;
	public ArrayList<Spike> spikes;
	public ArrayList<Canon> canons;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	

	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadImgs();
	}
	
	public void checkObjectTounched(Rectangle2D.Float hitbox) {
		for (Potion p : potions) {
			if (p.isActive()) {
				if (hitbox.intersects(p.getHitbox())){
					p.setActive(false);
					applyEffectToPlayer(p);
				}
			}
		}
	}
	
	public void applyEffectToPlayer(Potion p) {
		if (p.getObjType() == RED_POTION)
			playing.getPlayer().changeHealth(RED_POTION_VALUE);
		else 
			playing.getPlayer().changePower(BLUE_POTION_VALUE);
	}
	
	public void checkSpikesTounched(player p) {
		for (Spike s : spikes) {
			if (s.getHitbox().intersects(p.getHitbox())) {
				p.kill();
			}
		}
	}
	
	public void checkObjectHit(Rectangle2D.Float attackBox) {
		for (GameContainer gc : containers) {
			if (gc.isActive() && !gc.doAnimation) {
				if (gc.getHitbox().intersects(attackBox)) {
					gc.setAnimation(true);
					int type =0;
					if (gc.getObjType() == BARREL) {
						type =1;
					}
					potions.add(new Potion((int)(gc.getHitbox().x+ gc.getHitbox().width/2), 
							(int)(gc.getHitbox().y - gc.getHitbox().height /2),
							type));
					return;
				}
			}
		}
	}


	private void loadImgs() {
		BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTIONS_ATLAS);
		potionImg = new BufferedImage[2][7];
		
		for (int j=0; j< potionImg.length ; j++)
			for (int i=0; i< potionImg[j].length; i++) {
				potionImg[j][i] = potionSprite.getSubimage(12 * i, 16* j, 12, 16);
			}
		
		BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
		containerImg = new BufferedImage[2][8];
		
		for (int j=0; j< containerImg.length ; j++)
			for (int i=0; i< containerImg[j].length; i++) {
				containerImg[j][i] = containerSprite.getSubimage(40 * i, 30* j, 40, 30);
			}
		
		spikeImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);
		
		canonImg = new BufferedImage[7];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CANON_ATLAS);
		
		for (int i=0; i< canonImg.length; i++) {
			canonImg[i] = temp.getSubimage(i * 40, 0, 40, 26);
		}
		
		canonBallImg = LoadSave.GetSpriteAtlas(LoadSave.CANON_BALL);
		
		
	}
	
	public void update (int[][] lvData, player Player) {
		for (Potion p : potions) {
			if (p.isActive())
				p.update();
		}
		
		for (GameContainer gc : containers) {
			if (gc.isActive())
				gc.update();
		}
		
		updateCanons(lvData, Player);
		updateProjectile(lvData, Player);
	}
	
	private void updateProjectile(int[][] lvData, player player) {
		for (Projectile p : projectiles) {
			if (p.isActive()) {
				p.updatePos();
				if (p.getHitbox().intersects(player.getHitbox())) {
					player.changeHealth(-25);
					p.setActive(false);
				}
				else if ( IsProjectileHittingLevel(p, lvData)) {
					p.setActive(false);
				}
			}
		}
		
	}

	private void updateCanons(int[][] lvData, player p) {
		for (Canon c : canons) {
			if (!c.doAnimation)
				if (c.getTyleY() == p.getTyleY())
					if (isPlayerRange(c, p))
						if (isPlayerInFontOfCanon(c, p))
							if (canCanonSeePlayer(lvData, p.getHitbox(), c.getHitbox(), c.getTyleY())) {
								c.setAnimation(true);
							}

			c.update();
			if (c.getAniIndex() ==4 && c.getAniTick() ==0) {
				shootCanon(c);
			}
		}
	}

	private void shootCanon(Canon c) {
		c.setAnimation(true);	
		int dir =1;
		if (c.getObjType() == CANON_LEFT)
			dir = -1;
		projectiles.add(new Projectile((int)(c.getHitbox().x),(int)( c.getHitbox().y), dir));
		
	}

	private boolean isPlayerInFontOfCanon(Canon c, player p) {
		if (c.getObjType() == CANON_LEFT) {
			if (c.getHitbox().x > p.getHitbox().x)
				return true;

		} else if (c.getHitbox().x < p.getHitbox().x)
			return true;
		return false;
	}

	private boolean isPlayerRange(Canon c, player p) {
		
		int absValue =(int) Math.abs((p.getHitbox().x - c.getHitbox().x));
		return absValue <= game.TILES_SIZE *5;
	}

	public void draw(Graphics g, int xLvOffset) {
		drawPotions(g, xLvOffset);
		drawContainers(g, xLvOffset);
		drawTrap(g, xLvOffset);
		drawCanon(g, xLvOffset);
		drawProjectile(g, xLvOffset);
	}


	private void drawProjectile(Graphics g, int xLvOffset) {
		for (Projectile p : projectiles) {
			if (p.isActive()) {
				g.drawImage(canonBallImg, (int)(p.getHitbox().x -xLvOffset), (int) (p.getHitbox().y), CANON_BALL_WIDTH, CANON_BALL_HEIGHT, null);
			}
		}
		
	}

	private void drawCanon(Graphics g, int xLvOffset) {
		for (Canon c : canons) {
			int x = (int) (c.getHitbox().x - xLvOffset);
			int width = CANON_WIDTH;
			
			if (c.getObjType()  == CANON_RIGHT) {
				x+= width;
				width *= -1;
			}
			g.drawImage(canonImg[c.getAniIndex()], x, (int) (c.getHitbox().y), width, CANON_HEIGHT, null);
		}
		
	}

	private void drawTrap(Graphics g, int xLvOffset) {
		for (Spike s : spikes) {
			g.drawImage(spikeImg,(int) (s.getHitbox().x - xLvOffset), (int)(s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
		}
		
	}

	private void drawContainers(Graphics g, int xLvOffset) {
		for (GameContainer gc : containers) {
			if (gc.isActive()) {
				int type =0;
				if (gc.getObjType()== BARREL) {
					type =1;
				}
				g.drawImage(containerImg[type][gc.getAniIndex()], 
						(int) gc.getHitbox().x - gc.getxDrawOffset()- xLvOffset,
						(int) gc.getHitbox().y - gc.getyDrawOffset(),
						CONTAINER_WIDTH, 
						CONTAINER_HEIGHT, 
						null);
			}
		}
		
	}


	private void drawPotions(Graphics g, int xLvOffset) {
		for (Potion p : potions) {
			if (p.isActive()) {
				int type =0;
				if (p.getObjType() == RED_POTION)
					type = 1;
				
				g.drawImage(potionImg[type][p.getAniIndex()], 
						(int) p.getHitbox().x - p.getxDrawOffset()- xLvOffset,
						(int) p.getHitbox().y - p.getyDrawOffset(),
						POTION_WIDTH, 
						POTION_HEIGHT, 
						null);
			}
				
			
		}
		
	}


	public void loadObjects(level newLevel) {
		potions = new ArrayList<>(newLevel.getPotions());
		containers = new ArrayList<>(newLevel.getContainers());
		spikes = newLevel.getSpikes();
		canons = newLevel.getCanons();
		projectiles.clear();
		
	}

	public void resetAllObject() {
		loadObjects(playing.getLevelManager().getCurrentLevel());
		
		for (Potion p : potions) {
			p.reset();
		}
		
		for (GameContainer gc : containers) {
			gc.reset();
		}
		
		for (Canon c : canons) {
			c.reset();
		}
		
	}
	
	
}
