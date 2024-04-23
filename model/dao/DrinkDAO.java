package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.entity.Category;
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
	
	public Drink find(String id) {
		for(Drink i : list) {
			if(i.getID().equals(id))
				return i;
		}
		return new Drink("");
	}
	
	public List<Drink> returnListbyCategory(Category c){
		if(c.equals(Category.ALL))
			return list;
		List<Drink> tmp = new ArrayList<Drink>();
		for(Drink i : list) {
			if(i.getCategory().equals(c))
				tmp.add(i);
		}
		return tmp;
	}
}
