package me.huding.designpattern.composite;

public class Client {

	public static void main(String[] args) {
		Composite root = new Composite();
		root.action();
		
		Composite branch = new Composite();
		Leaf leaf = new Leaf();
		
		root.add(branch);
		root.add(leaf);
	}
	
	public static void display(Composite composite){
		for(Component component : composite.getComponents()){
			if(component instanceof Leaf){
				component.action();
			}
			else {
				component.action();
				display((Composite)component);
			}
		}
	}

}
