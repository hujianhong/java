package me.huding.aop;

public class CPRMBPay implements IPay {
	

	@Override
	public void pay() {
		System.out.println("使用人民币支付");
		
	}

}
