package me.huding.common.unsafe;


import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeTest {
	
	private String hu;
	
	public static void main(String[] args) throws InstantiationException, NoSuchFieldException, SecurityException {
		UnsafeTest unsafeTest = (UnsafeTest) unsafe.allocateInstance(UnsafeTest.class);
		
		Field field = UnsafeTest.class.getDeclaredField("hu");
		unsafe.putObject(unsafeTest, unsafe.objectFieldOffset(field), "hujianhong");
		
		System.out.println(unsafeTest.hu);
		
		System.out.println(unsafe.pageSize());
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("start to await");
				unsafe.park(false, 0);
				System.out.println("i am back!");
				
			}
		});
		thread.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		unsafe.unpark(thread);
		
	}
	
	
	static sun.misc.Unsafe unsafe;
	
	
	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
