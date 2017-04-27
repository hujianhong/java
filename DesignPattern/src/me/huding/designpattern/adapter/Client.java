package me.huding.designpattern.adapter;

public class Client {

	public static void main(String[] args) {
		Target target = new Adaptor();
		
		target.request();
	}

}
