package me.huding.aop;

public class ARMBPay  extends AbstractPay {



	@Override
	public void doPay() {
		System.out.println("使用人民币支付");
	}

}
