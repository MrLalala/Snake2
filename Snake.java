package com.Snake.Gan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Snake {
	Node head = null;
	Node tail = null;
	int size = 0;
	
	
	
	private Node n = new Node(10,10,Direction.D);
	
	public Snake(){
		this.head = n;
		this.tail = n;
		size = 1;
	}
	
	public void addTail(){
		Node node = null;
		switch(tail.dir){
		case L:
			node = new Node(tail.row,tail.col+1,tail.dir);
			break;
		case R:
			node = new Node(tail.row,tail.col-1,tail.dir);
			break;
		case U:
			node = new Node(tail.row+1,tail.col,tail.dir);
			break;
		case D:
			node = new Node(tail.row-1,tail.col,tail.dir);
			break;
		}
		tail.next = node;
		node.prev = tail;
		tail = node;
		size++;
	}
	
	public void addHead(){
		Node node = null;
		switch(head.dir){
		case D:
			node = new Node(head.row+1,head.col,head.dir);
			break;
		case U:
			node = new Node(head.row-1,head.col,head.dir);
			break;
		case R:
			node = new Node(head.row,head.col+1,head.dir);
			break;
		case L:
			node = new Node(head.row,head.col-1,head.dir);
			break;
		}
		node.next = head;
		head.prev = node;
		head = node;
		size++;
	}
	
	public void draw(Graphics g){
		move();
		if(size<=0)return;
		Color c = g.getColor();
		g.setColor(Color.gray);
		g.fillRect(head.col*Yard.BLOCK,head.row*Yard.BLOCK,Yard.BLOCK,Yard.BLOCK);
		g.setColor(c);
		for(Node n = head;n != null;n = n.next){
			n.draw(g);
		}
	}

	
	
	public void move(){
		addHead();
		deleteTail();
	}

	public boolean checkHead() {
		if(head.row < 2 || head.row > Yard.ROWS-2 || head.col < 0 || head.col >Yard.COLS-2)
			return false;
		for(Node n = head.next;n != null;n = n.next){
			if(head.row == n.row && head.col == n.col)
				return false;
		}
		return true;
	}
	
	private class Node{
		int w = Yard.BLOCK;
		int h = Yard.BLOCK;
		int row,col;
		Node next = null;
		Node prev = null;
		Direction dir = Direction.L;
		Node(int row, int col,Direction dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
		void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.red);
			g.fillRect(Yard.BLOCK*col, Yard.BLOCK*row, w, h);
			g.setColor(c);
		}
	}
	private void deleteTail() {
		if(size == 0)
			return;
		tail = tail.prev;
		tail.next = null;
		
	}

	public void keyPressed(KeyEvent e) {
		 int key = e.getKeyCode();
		 switch(key){
		 case KeyEvent.VK_RIGHT:
			 if(head.dir != Direction.L)
				 this.head.dir = Direction.R;
			 break;
		 case KeyEvent.VK_LEFT:
			 if(head.dir != Direction.R)
				 this.head.dir = Direction.L;
			 break;
		 case KeyEvent.VK_UP:
			 if(head.dir != Direction.D)
				 this.head.dir = Direction.U;
			 break;
		 case KeyEvent.VK_DOWN:
			 if(head.dir != Direction.U)
				 this.head.dir = Direction.D;
			 break;
		 }
	}
	
	public Rectangle getRect(){
		return new Rectangle(this.head.col*Yard.BLOCK,this.head.row*Yard.BLOCK,Yard.BLOCK,Yard.BLOCK);
	}
	
	public void eatEgg(Egg e){
		if(e.isLive()&&e.getRect().intersects(this.getRect())){
			e.setLive(false);
			this.addHead();
		}
			
	}
}
