package tp4;

public class Article {
	
	private String name;
	private int price;
	
	public Article(String name, int price) {
		this.name = name;
		this.price = price;
	}
	
	public int getPrix() {
		return price;
	}
	public void setPrix(int prix) {
		this.price = prix;
	}
	public String getNom() {
		return name;
	}
	public void setNom(String nom) {
		this.name = nom;
	}
	
	

}
