package me.huding.common.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import java.util.Set;

public class TestPhantomReference {
	

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		ReferenceQueue<Bean> referenceQueue = new ReferenceQueue<>();
		Set<PhantomReference<Bean>> set = new HashSet<>();
		for(int i = 0;i < 10;i ++){
			PhantomReference<Bean> reference = new PhantomReference<Bean>(new Bean("phantom" + i),referenceQueue);
			System.out.println("just create:" + reference.get());
			set.add(reference);
		}
		System.gc();
		// 等待1000ms,测试是否会回收虚引用
		Thread.sleep(1000);
		Reference<? extends Bean> reference = referenceQueue.poll();
		
		if(reference != null){
			System.out.println("in queue : " + reference.get());
		} else {
			System.out.println("not in queue");
		}
	}

}
