package me.huding.common.threadlocal;

public class ThreadLocalDemo {

	
	
	public static void main(String[] args) {
		
		ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
			@Override
			protected Integer initialValue() {
				return 1;
			}
		};
		
		for(int i = 0;i < 5;i ++){
			new Thread(()->{
				int count = threadLocal.get();
				for(int  k = 0;k < 5;k ++){
					count++;
					System.out.println(Thread.currentThread().getName() + "\t" + count);
				}
			},"thread-" + i).start();
		}
	}

}
