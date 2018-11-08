package me.huding.designpattern.flyweight;

public class ConcreteFlyweight implements Flyweight {
	
	private String istate;
	
	public ConcreteFlyweight(String istate) {
		this.istate = istate;
	}

	@Override
	public void action(String estate) {
		// TODO Auto-generated method stub
		System.out.println(istate + "\t" + estate);
	}

}
