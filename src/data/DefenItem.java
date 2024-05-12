package data;

public class DefenItem extends itemShop{
	private int deflect;
	private int maxUse;
	
	public DefenItem(String id, String name, int price, int deflect, int maxUse) {
		super(id, name, price);
		this.deflect = deflect;
		this.maxUse = maxUse;
	}
	public int getDeflect() {
		return deflect;
	}
	public void setDeflect(int deflect) {
		this.deflect = deflect;
	}
	public int getMaxUse() {
		return maxUse;
	}
	public void setMaxUse(int maxUse) {
		this.maxUse = maxUse;
	}

}
