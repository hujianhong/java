package me.huding.aop;

public class CPayDecorator extends PayDecorator {

	public CPayDecorator(IPay delegate) {
		super(delegate);
	}
	
	
	private void log(){
		System.out.println("记录日志开始");
	}
	
	private void startTimeCount(){
		System.out.println("时间统计开始");
	}
	
	private void endTimeCount(){
		System.out.println("时间统计结束");
	}
	
	
	
	@Override
	public void pay() {
		log();
		startTimeCount();
		super.pay();
		endTimeCount();
	}

}
