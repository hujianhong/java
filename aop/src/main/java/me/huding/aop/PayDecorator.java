package me.huding.aop;

/**
 * 
 * @author jianhonghu
 *
 */
public abstract class PayDecorator implements IPay {
	
	private IPay delegate;
	
	public PayDecorator(IPay delegate) {
		this.delegate = delegate;
	}

	@Override
	public void pay() {
		delegate.pay();
	}

}
