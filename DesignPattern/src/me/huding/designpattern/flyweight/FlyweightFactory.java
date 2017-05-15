package me.huding.designpattern.flyweight;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {
	private static Map<String, Flyweight> map = new HashMap<>();
	
	private FlyweightFactory(){}
	
	public static Flyweight getFlyweight(String istate){
		Flyweight flyweight = map.get(istate);
		if(flyweight == null){
			flyweight = new ConcreteFlyweight(istate);
			map.put(istate, flyweight);
		}
		return flyweight;
	}
}
