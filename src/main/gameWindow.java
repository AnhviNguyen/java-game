package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

// jframe: chua toan bo giao dien cua nguoi dung
public class gameWindow {
	private JFrame jfame;

	public gameWindow(gamePanel gamepanel) {
		jfame = new JFrame();
		
		jfame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfame.add(gamepanel);
		//hien thi kich thuoc phu hop voi cac thanh phan con cua no (border, lyaout,..)
		jfame.pack();
		jfame.setResizable(false);
		//hien thi sao cho o trung tam man hinh may tinh
		jfame.setLocationRelativeTo(null);
		jfame.setVisible(true);
		//dung de xu ly cac su kien khi window bi lost focus
		jfame.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			// dung de su dung khi game dang o trang thai lost focus (se khoi dong lai tu dau)
			public void windowLostFocus(WindowEvent e) {
				gamepanel.getGame().windowfocusLost();
				
			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
}
