package com.Snake.Gan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Egg {
	private int row,col;
	/**
	 * @see #isLive()
	 * @see Egg#setLive(boolean)
	 */
	private boolean live = true;
	private static Random r = new Random();
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Egg(){
		this(r.nextInt(Yard.ROWS-3)+3,r.nextInt(Yard.COLS-3)+3);
	}
	public Egg(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.BLUE);
		g.fillOval(Yard.BLOCK*col, Yard.BLOCK*row, Yard.BLOCK, Yard.BLOCK);
		g.setColor(c);
	}
	
	public Rectangle getRect(){
		return new Rectangle(this.col*Yard.BLOCK,this.row*Yard.BLOCK,Yard.BLOCK,Yard.BLOCK);
	}
	
	
}
