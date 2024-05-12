package data;

import implementation.helper;

public class User extends Player implements helper{
	private int posX;
	private int posY;

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public User(String email, String password, int posX, int posY) {
		super(email, password);
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public void space() {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");	
	}

	@Override
	public void sleep() {
		try{
		    Thread.sleep(1000);
		}catch (InterruptedException e) {
		    e.printStackTrace();
		}
	}	
}
