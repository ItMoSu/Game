package data;

public class PlayerAttributes extends Entity{
	private int money;

	public PlayerAttributes(String name, double damage, double mana, double health, int money) {
		super(name, damage, mana, health);
		this.money = money;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
}
