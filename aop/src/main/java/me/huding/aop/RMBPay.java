package me.huding.aop;

public class RMBPay implements IPay {
	
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
		System.out.println("使用人民币支付");
		endTimeCount();
		
	}

}
