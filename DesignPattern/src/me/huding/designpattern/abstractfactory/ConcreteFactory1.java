package me.huding.designpattern.abstractfactory;

public class ConcreteFactory1 implements AbstractFactory {

	@Override
	public ProductA factoryA() {
		// TODO Auto-generated method stub
		return new ProductA1();
	}

	@Override
	public ProductB factoryB() {
		// TODO Auto-generated method stub
		return new ProductB1();
	}

}
