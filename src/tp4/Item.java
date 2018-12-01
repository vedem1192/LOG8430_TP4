package tp4;

public class Item {
	
	private String name;
	private int price;
	private int qty;
	
	public Item(String name, int price, int qty) {
		this.name = name;
		this.price = price;
		this.qty = qty;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	
	public int getQuantity() {
		return qty;
	}
}
