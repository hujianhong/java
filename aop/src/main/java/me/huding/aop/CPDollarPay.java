package me.huding.aop;

public class CPDollarPay implements IPay {


	@Override
	public void pay() {
		System.out.println("使用美元支付");
	}

}
