package ultilz;

import java.awt.Color;
import static ultilz.constants.EnemyConstants.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entities.Crabby;
import main.game;

public class LoadSave {
	// ten bien ung voi dia chi (ten anh)  tuong ung
	public static final String PLAYER_ATLAS = "player_sprites.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String MENU_BUTTON = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTON = "sound_button.png";
	public static final String URM_BUTTON = "urm_buttons.png";
	public static final String VOLUME_BUTTON = "volume_buttons.png";
	public static final String MENU_BACKGROUND_IMG = "background_menu.png";
	public static final String PLAYING_BG_IMG = "playing_bg_img.png";
	public static final String BIG_CLOUD = "big_clouds.png";
	public static final String SMALL_CLOUD = "small_clouds.png";
	public static final String GRABBY_SPRITE = "crabby_sprite.png";
	public static final String STATUS_BAR = "health_power_bar.png";
	public static final String COMPLETE_IMG = "completed_sprite.png";
	
	public static final String POTIONS_ATLAS = "potions_sprites.png";
	public static final String CONTAINER_ATLAS = "objects_sprites.png";
	public static final String TRAP_ATLAS = "trap_atlas.png";
	public static final String CANON_ATLAS = "cannon_atlas.png";
	public static final String CANON_BALL = "ball.png";
	public static final String DEATH_SCREEN = "death_screen.png";
	public static final String OPTION_MENU = "options_background.png";
	
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		// lay file
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
			// doc no va luu tru trong bien
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// tra ve file anh
		return img;
	}
	
	public static BufferedImage[] getAllLevels() {
		URL url = LoadSave.class.getResource("/lvls");
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		File[] files = file.listFiles();
		File[] filesSort = new File[files.length];
		
		// sap xep cac level theo thu tu tang dan
		for (int i=0; i< filesSort.length; i++) {
			for (int j=0; j< files.length; j++ ) {
				if (files[j].getName().equals((i+1)+ ".png"))
					filesSort[i] = files[j];
			}
		}
		
		BufferedImage[] img = new BufferedImage[filesSort.length];
		// luu cac file anh theo files da duoc sap xep
		for (int i=0; i< img.length; i++) {
			try {
				img[i] = ImageIO.read(filesSort[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return img;
	}
	
	


}


