package me.huding.aop;

public abstract class AbstractPay implements IPay {

	private void log(){
		System.out.println("记录日志开始");
	}
	
	private void startTimeCount(){
		System.out.println("时间统计开始");
	}
	
	private void endTimeCount(){
		System.out.println("时间统计结束");
	}
	
	private void checkAuth(){
		System.out.println("安全检查");
	}

	@Override
	public void pay() {
		log();
		startTimeCount();
		checkAuth();
		doPay();
		endTimeCount();
	}
	
	protected abstract void doPay();

}
