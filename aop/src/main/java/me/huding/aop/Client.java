package me.huding.aop;

public class Client {

	public static void main(String[] args) {
		System.out.println("-----------最原始解决方案-----------------");
		/*
		 * 最原始解决方案：
		 * 缺点：太多重复代码，且紧耦合
		 * 
		 */
		IPay dollarPay = new DollarPay();
		IPay rmbPay = new RMBPay();
		dollarPay.pay();
		System.out.println("*********");
		rmbPay.pay();
		
		System.out.println("-----------抽象类共性设计解决方案-----------------");
		
		/*
		 * 抽象类共性设计解决方案：
		 * 缺点：一荣俱荣，一损俱损
		 */
		IPay dollarPay1 = new ADollarPay();
		IPay rmbPay1 = new ARMBPay();
		dollarPay1.pay();
		System.out.println("*********");
		rmbPay1.pay();
		
		System.out.println("------使用装饰器模式/代理模式改进的解决方案----------------------");
		
		/*
		 * 使用装饰器模式/代理模式改进的解决方案:
		 * 
		 * 装饰器模式：动态地给一个对象添加一些额外的职责。就增加功能来说， 装饰器模式相比生成子类更为灵活。
		 * 
		 * 代理模式：为其他对象提供一种代理以控制对这个对象的访问。
		 * 
		 * 缺点：紧耦合，每个业务逻辑需要一个装饰器实现或代理
		 */
		IPay dollarPay2 = new CPayDecorator(new PayProxy(new CPDollarPay()));
		IPay rmbPay2 = new CPayDecorator(new PayProxy(new CPRMBPay()));
		dollarPay2.pay();
		System.out.println("*********");
		rmbPay2.pay();
		
		
		System.out.println("-----------JDK动态代理解决方案-----------------");
		
		/*
		 * JDK动态代理解决方案（比较通用的解决方案）
		 * 
		 * 
		 * 缺点：使用麻烦，不能代理类，只能代理接口  
		 */
		IPay dollarPay3 = (IPay)MyInvocationHandler.proxy(new CPDollarPay());
		IPay rmbPay3 =  (IPay)MyInvocationHandler.proxy(new CPRMBPay());
		dollarPay3.pay();
		System.out.println("*********");
		rmbPay3.pay();
		
		System.out.println("-----------CGLIB动态代理解决方案（比较通用的解决方案）-----------------");
		
		/*
		 * CGLIB动态代理解决方案（比较通用的解决方案）
		 * 
		 * 优点：能代理接口和类
		 * 缺点：使用麻烦，不能代理final类 
		 * 
		 * 
		 * 动态代理本质 
			本质：对目标对象增强
           	最终表现为类（动态创建子类），看手工生成（子类）还是自动生成（子类）
			代理限制：
           		只能在父类方法被调用之前或之后进行增强（功能的修改），不能在中间进行修改，要想在方法调用中增强，需要ASM(java 字节码生成库)
			其他动态代理框架
				jboss：javassist （hibernate 3.3中默认为javassist）
                           		（hibernate 3.3之前中默认为cglib）
		 */
		IPay dollarPay4 = (IPay)MyInvocationHandler.proxy(new CPDollarPay());
		IPay rmbPay4 =  (IPay)MyInvocationHandler.proxy(new CPRMBPay());
		dollarPay4.pay();
		System.out.println("*********");
		rmbPay4.pay();

	}

}
