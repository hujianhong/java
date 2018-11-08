package me.huding.designpattern.abstractfactory;

public class ConcreteFactory2 implements AbstractFactory {

	@Override
	public ProductA factoryA() {
		// TODO Auto-generated method stub
		return new ProductA2();
	}

	@Override
	public ProductB factoryB() {
		// TODO Auto-generated method stub
		return new ProductB2();
	}

}
