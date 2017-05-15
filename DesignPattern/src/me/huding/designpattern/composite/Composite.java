package me.huding.designpattern.composite;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Component {
	
	private List<Component> components = new ArrayList<>();

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}
	
	
	public void add(Component comonent){
		components.add(comonent);
	}
	
	public void remove(Component comonent){
		components.remove(comonent);
	}


	public List<Component> getComponents() {
		return components;
	}
	
	

}
