package me.huding.designpattern.adapter;

public class Adaptor extends Adaptee implements Target {

	@Override
	public void request() {
		this.specificRequest();
		
	}

}
