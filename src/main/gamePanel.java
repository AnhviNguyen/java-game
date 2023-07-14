package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.keyboardInputs;
import inputs.mouseInputs;
import static ultilz.constants.PlayerConstants.*;
import static ultilz.constants.Direction.*;
import static main.game.GAME_HEIGHT;
import static main.game.GAME_WIDTH;;

@SuppressWarnings("serial")
public class gamePanel extends JPanel{
	private mouseInputs mouseinputs;
	private game Game;

	// jpanel : quan ly cac thanh phan trong giao dien nhu button, image,...
	public gamePanel(game Game) {
		mouseinputs = new mouseInputs(this);
		this.Game = Game;
		setPanelSize();
		addKeyListener(new keyboardInputs(this));
		addMouseListener(mouseinputs);
		addMouseMotionListener(mouseinputs);
	}

	// thiet lap container cho game 
	public void setPanelSize() {
		// obj dimension xac dinh kich thuoc cua game
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		//thoet lap kich thuoc uu tien thanh size
		setPreferredSize(size);
	}
	
	public void paintComponent(Graphics g) {
		// xoa va ve lai cac thanh phan cua g
		super.paintComponent(g);
		// ve len bang lenh render trong obj game
		Game.render(g);
	}
	
	public game getGame() {
		return Game;
	}
}
