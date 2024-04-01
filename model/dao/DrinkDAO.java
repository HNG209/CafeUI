package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.entity.Drink;

public class DrinkDAO {
	private List<Drink> list;
	public DrinkDAO() {
		list = new ArrayList<Drink>();
	}
	public boolean add(Drink d) {
		if(list.contains(d)) return false;
		list.add(d);
		return true;
	}
	
	public List<Drink> getList(){
		return list;
	}
}
