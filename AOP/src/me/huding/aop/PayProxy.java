package me.huding.aop;

public class PayProxy implements IPay {
	
	private IPay target;
	
	public PayProxy(IPay target) {
		this.target = target;
	}

	@Override
	public void pay() {
		// TODO Auto-generated method stub
		this.checkAuth();
		target.pay();

	}
	
	private void checkAuth(){
		System.out.println("安全检查");
	}

}
