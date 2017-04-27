package me.huding.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler {

	private Object target;

	public MyInvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		log();
		startTimeCount();
		checkAuth();
		Object result = method.invoke(target, args);
		endTimeCount();
		return result;
	}

	private void log() {
		System.out.println("记录日志开始");
	}

	private void startTimeCount() {
		System.out.println("时间统计开始");
	}

	private void endTimeCount() {
		System.out.println("时间统计结束");
	}

	private void checkAuth() {
		System.out.println("安全检查");
	}

	public static Object proxy(Object target) {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
				new MyInvocationHandler(target));
	}

}
