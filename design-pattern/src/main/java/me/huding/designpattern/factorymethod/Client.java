package me.huding.designpattern.factorymethod;

public class Client {

	public static void main(String[] args) {
		IFactory factory = new Factory();
		IProduct product = factory.createProduct();
		product.productMethod();

	}

}
