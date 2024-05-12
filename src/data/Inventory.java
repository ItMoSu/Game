package data;

public class Inventory extends itemShop{
	private String type;
	private double damage;
	private int maxOrMana;
	private int useLeft;
	private int owner;

	public Inventory(String id, String name, int price, String type, double damage, int maxOrMana, int useLeft,
			int owner) {
		super(id, name, price);
		this.type = type;
		this.damage = damage;
		this.maxOrMana = maxOrMana;
		this.useLeft = useLeft;
		this.owner = owner;
	}
	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getDamage() {
		return damage;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}
	public int getMaxOrMana() {
		return maxOrMana;
	}
	public void setMaxOrMana(int maxOrMana) {
		this.maxOrMana = maxOrMana;
	}
	public int getUseLeft() {
		return useLeft;
	}
	public void setUseLeft(int useLeft) {
		this.useLeft = useLeft;
	}
	
}
