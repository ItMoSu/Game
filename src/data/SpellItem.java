package data;

public class SpellItem extends itemShop{
	private int damage;
	private int mana;
	
	public SpellItem(String id, String name, int price, int damage, int mana) {
		super(id, name, price);
		this.damage = damage;
		this.mana = mana;
	}
	
	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
