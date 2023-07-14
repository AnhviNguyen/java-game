package gameState;

// enum : kieu du lieu dac biet trong java do nguoi dung tu dinh nghia de thiet lap cac trang thai cua tro choi
public enum gameState {
	PLAYING, MENU, OPTIONS, QUIT;
	
	// voi gia tri thiet lap ban dau la menu
	public static gameState state = MENU;	
}
