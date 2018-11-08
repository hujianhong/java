package me.huding.designpattern.bridge;

public class Client {

	public static void main(String[] args) {
		Implementor implementor = new ConcreteImplementor();
		Abstraction abstraction = new RefinedAbstraction(implementor);
		
		abstraction.action();

	}

}
