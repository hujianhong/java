package me.huding.aop;

public class ADollarPay extends AbstractPay {



	@Override
	public void doPay() {
		System.out.println("使用美元支付");
	}

}
