package tp4;

public class Item {
	
	private String name;
	private double price;
	private int qty;
	
	public Item(String name, int price, int qty) {
		this.name = name;
		this.price = price;
		this.qty = qty;
	}
	
	public double getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	
	public int getQuantity() {
		return qty;
	}
	
	
	@Override
	public String toString() {
		return "Item : " + name + ", Price : " + price + ", Quatity : " + qty;
	}
}
