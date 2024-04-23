package model.entity;

import java.util.Objects;

public class Drink {
	private String ID;
	private String name;
	private double price;
	private String description;
	private Category category;
	private String imgPath;
	
	public Drink(String iD, String name, double price, String description, Category c, String imgPath) {
		super();
		ID = iD;
		this.name = name;
		this.price = price;
		this.description = description;
		this.category = c;
		this.imgPath = imgPath;
	}
	
	public Drink() {
		this("", "", 0.0, "", Category.ALL, "");
	}
	
	public Drink(String iD) {
		this(iD, "", 0.0, "", Category.ALL, "");
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Drink other = (Drink) obj;
		return Objects.equals(ID, other.ID);
	}

	@Override
	public String toString() {
		return "Drink [ID=" + ID + ", name=" + name + ", price=" + price + ", description=" + description
				+ ", category=" + category + ", imgPath=" + imgPath + "]";
	}
	
	
}
