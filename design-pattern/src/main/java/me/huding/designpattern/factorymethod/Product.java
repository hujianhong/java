package me.huding.designpattern.factorymethod;

public class Product implements IProduct {

	@Override
	public void productMethod() {
		System.out.println("I'm a concrete product!");
	}

}
