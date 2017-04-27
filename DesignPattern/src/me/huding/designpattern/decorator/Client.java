package me.huding.designpattern.decorator;

public class Client {

	public static void main(String[] args) {
		Component component = new ConcreteComponent();
		
		// 进行装饰
		Decorator decorator = new ConcreteDecorator(component);
		
		decorator.action();

	}

}
