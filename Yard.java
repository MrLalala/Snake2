package com.Snake.Gan;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Yard extends Frame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int score = 1;
	
	static final int COLS = 40;

	static final int ROWS = 30;

	static final int BLOCK = 20;
	
	private boolean flag = true;
	
	private Image offScreen = null;

	static final Color COLOR = Color.gray;
	
	Snake s = new Snake();

	Egg egg = new Egg(12,10);
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(COLOR);
		g.fillRect(0,0, COLS*BLOCK, ROWS*BLOCK);
		g.setColor(Color.BLACK);
		for(int i = 1;i<ROWS;i++){
			g.drawLine(0, BLOCK*(i), COLS*BLOCK, BLOCK*(i));
		}
		for(int i = 1;i<COLS;i++){
			g.drawLine((i)*BLOCK,0,BLOCK*(i),ROWS*BLOCK);
		}
		g.setColor(Color.YELLOW);
		g.drawString("Score :"+score, 20,40);
		s.draw(g);
		egg.draw(g);
		s.eatEgg(egg);
		if(!egg.isLive()){
			egg = new Egg();
			egg.draw(g);
			score +=1;
		}
		if((flag = s.checkHead()) == false){
			g.setFont(new Font("»ªÎÄ²ÊÔÆ",Font.BOLD,30));
			g.drawString("Game Over",300,300);
		}
		g.setColor(c);
		
	}
	
	public void update(Graphics g){ 
		if(offScreen == null){
			offScreen = this.createImage(COLS*BLOCK,ROWS*BLOCK);
		}
		Graphics goff = offScreen.getGraphics();
		paint(goff);
		g.drawImage(offScreen, 0, 0, null);
	}
	
	private class move implements Runnable {
		public void run() {
			while (flag) {
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void launch(){
		this.setLocation(100,200);
		this.setSize(COLS*BLOCK,ROWS*BLOCK);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setVisible(true);
		this.addKeyListener(new myKeyMonitor());
		new Thread(new move()).start();
	}
	
	private class myKeyMonitor extends KeyAdapter{

		public void keyPressed(KeyEvent e) {
			s.keyPressed(e);
			if(false == flag && e.getKeyCode() == KeyEvent.VK_F2){
				score = 0;
				s = new Snake();
				egg = new Egg();
				flag = true;
				new Thread(new move()).start();
			}
				
		}

		public void keyReleased(KeyEvent e) {
		}
		
	}
	public static void main(String[] args) {
		new Yard().launch();
	}
	


}
