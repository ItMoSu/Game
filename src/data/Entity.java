package data;

public abstract class Entity {
	private String name;
	private double damage;
	private double mana;
	private double health;
	
	public Entity(String name, double damage, double mana, double health) {
		super();
		this.name = name;
		this.damage = damage;
		this.mana = mana;
		this.health = health;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		this.mana = mana;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}
	
}
