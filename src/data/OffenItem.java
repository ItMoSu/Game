package data;

public class OffenItem extends itemShop{
	private int damage;
	private int maxUse;
	
	public OffenItem(String id, String name, int price, int damage, int maxUse) {
		super(id, name, price);
		this.damage = damage;
		this.maxUse = maxUse;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getMaxUse() {
		return maxUse;
	}
	public void setMaxUse(int maxUse) {
		this.maxUse = maxUse;
	}
	
	
}
