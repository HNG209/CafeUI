package model.entity;

public enum Category {
	
	ALL("tất cả"),
	TRA_SUA("trà sữa"),
	CAFE("cà phê"),
	TRA("trà"),
	SINH_TO("sinh tố"),
	NUOC_TRAI_CAY("nước trái cây");
	
	private String name;
	private Category(String name) {
		this.name = name;
	}
	public String toString() {
		return this.name;
	}
}
