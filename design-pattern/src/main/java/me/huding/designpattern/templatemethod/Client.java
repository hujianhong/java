package me.huding.designpattern.templatemethod;

public class Client {

	public static void main(String[] args) {
		AbstractClass abstractClass = new ConcreteClass();
		
		abstractClass.action();

	}

}
