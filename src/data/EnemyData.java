package data;

public class EnemyData extends Entity{
	private double armorCrit;
	private String type;
	public EnemyData(String name, double damage, double mana, double health, double armorCrit, String type) {
		super(name, damage, mana, health);
		this.armorCrit = armorCrit;
		this.type = type;
	}
	public double getArmorCrit() {
		return armorCrit;
	}
	public void setArmorCrit(double armorCrit) {
		this.armorCrit = armorCrit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
