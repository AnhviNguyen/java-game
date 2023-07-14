package ultilz;

import main.game;

public class constants {
	// trong luc
	public static final  float GRAVITY = 0.04f *game.SCALE;
	// toc do chuyen dong tren 1 khung hinh
	public static final int ANI_SPEED = 25;
	
	public static class Projecttiles{
		// kich thuoc ca toc do cua dan 
		public static final int CANON_BALL_DEFALUT_WIDTH= 15;
		public static final int CANON_BALL_DEFAULT_HEIGHT= 15;
		
		public static final int CANON_BALL_WIDTH= (int) ( CANON_BALL_DEFALUT_WIDTH *game.SCALE);
		public static final int CANON_BALL_HEIGHT= (int) (CANON_BALL_DEFAULT_HEIGHT *game.SCALE);
		public static final float SPEED = 0.8f * game.SCALE;
	}
	
	public static class ObjectConstants{
		// cac thung chua vat pham 
		public static final int RED_POTION =0; // vat pham mau do
		public static final int BLUE_POTION =1; // vat pham mau xanh
		public static final int BARREL =2; // thung giong thung ruou
		public static final int BOX =3;	// thung giong hop kho bau
		public static final int SPIKE =4;
		public static final int CANON_LEFT = 5;
		public static final int CANON_RIGHT =6;
		
		public static final int RED_POTION_VALUE = 15;
		public static final int BLUE_POTION_VALUE= 10;
		
		public static final int CONTAINER_WIDTH_DEFAULT = 40;
		public static final int CONTAINER_HEIGHT_DEFAULT = 30;
		public static final int CONTAINER_WIDTH = (int) (game.SCALE *CONTAINER_WIDTH_DEFAULT);
		public static final int CONTAINER_HEIGHT = (int) (game.SCALE * CONTAINER_HEIGHT_DEFAULT);
		
		public static final int POTION_WIDTH_DEFAULT= 12;
		public static final int POTION_HEIGHT_DEFALUT = 16;
		public static final int POTION_WIDTH = (int)(game.SCALE * POTION_WIDTH_DEFAULT);
		public static final int POTION_HEIGHT = (int) (game.SCALE * POTION_HEIGHT_DEFALUT);
		
		public static final int SPIKE_WIDTH_DEFAULT = 32;
		public static final int SPIKE_HEIGHT_DEFAULT = 32;
		public static final int SPIKE_WIDTH  = (int) (game.SCALE * SPIKE_WIDTH_DEFAULT);
		public static final int SPIKE_HEIGHT =  (int)(game.SCALE * SPIKE_HEIGHT_DEFAULT);
		
		public static final int  CANON_WIDTH_DEFAULT = 40;
		public static final int CANON_HEIGHT_DEFAULT = 26;
		public static final int CANON_WIDTH = (int)(game.SCALE * CANON_WIDTH_DEFAULT);
		public static final int CANON_HEIGHT= (int) (game.SCALE * CANON_HEIGHT_DEFAULT);
		
		public static int GetSpriteAmount(int object_type) {
			switch (object_type) {
			case RED_POTION, BLUE_POTION:
				return 7;
			case BARREL, BOX:
				return 8;
			case CANON_RIGHT, CANON_LEFT:
				return 7;
			}
			return 1;
		}
		
	}
	// cac tu the cua crabby, toc do, kich co cua no
	public static class EnemyConstants{
		public static final int CRABBY =0;

		public static final int IDLE =0;
		public static final int RUNNING =1;
		public static final int ATTACK =2;
		public static final int HIT =3; 
		public static final int DEAD =4;
		
		public static final int CRABBY_WIDTH_DEFAULT = 72;
		public static final int CRABBY_HEIGHT_DEFALUT = 32;

		public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT *game.SCALE);
		public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFALUT *game.SCALE);
		
		public static final int CRABBY_DRAWOFFSET_X = (int)( 26 * game.SCALE);
		public static final int CRABBY_DRAWOFFSET_Y = (int)( 9 * game.SCALE);
		
		
		//  vi tri cac dong trong anh crabby (voi tung dong se la tung hanh dong (? co the la so cot lon nhat trong tung dong) khac nhau)
		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			switch(enemy_type) {
			case CRABBY:
				switch (enemy_state) {
				case IDLE:
					return 9;
				case RUNNING:
					return 6;
				case ATTACK:
					return 7;
				case HIT:
					return 4;
				case DEAD:
					return 5;
				}
			}
			return 0;
		}
		
		// 
		public static int GetMaxHealth(int enemy_type) {
			switch (enemy_type) {
			case CRABBY: {
				return 15;
			}
			default:
				return 0;
			}
		}
		
		public static int getEnemyDmg(int enemy_type) {
			switch (enemy_type) {
			case CRABBY: {
				return 10;
			}
			default:
				return 1;
			}
		}
	}
	
	
	public static class Evironment {
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;
		
		public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * game.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * game.SCALE);
		public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * game.SCALE);
		public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * game.SCALE);
	}
	
	public static class UI{ 
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * game.SCALE);
		}
		
		public static class PauseButtons{
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE =(int)(SOUND_SIZE_DEFAULT * game.SCALE);
		}
		
		public static class URMButtons{
			public static final int URM_DEFAULT_SIZE = 56; // kich co luc dau cua urm button
			public static final int URM_SIZE = (int)(URM_DEFAULT_SIZE * game.SCALE); // urm sau khi scale len 1.5
		}
		
		public static class VolumeButtons{
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT =44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;
			
			public static final int VOLUME_WIDTH = (int)(VOLUME_DEFAULT_WIDTH *game.SCALE);
			public static final int VOLUME_HEIGHT = (int)(VOLUME_DEFAULT_HEIGHT *game.SCALE);
			public static final int SLIDER_WIDTH = (int)(SLIDER_DEFAULT_WIDTH *game.SCALE);
			
		}
	}
	
	public static class Direction{
		public static final int LEFT =0;
		public static final int UP =1;
		public static final int RIGHT =2;
		public static final int DOWN =3;
		
	}
	
	public static class PlayerConstants{
		public static final int IDLE = 0;
		public static final int RUNNING =1;
		public static final int JUMP = 2;
		public static final int FALING =3;
		public static final int ATTACK = 4;
		public static final int HIT =5;
		public static final int DEAD =6;

		
		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case DEAD :
				return 8;
			case RUNNING:
				return 6;
			case IDLE:
				return 5;
			case HIT:
				return 4;
			case JUMP:
			case ATTACK:
				return 3;
			case FALING:
			default:
				return 1;
			}
			
		}
	}
}
