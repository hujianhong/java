package me.huding.common.reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

public class TestWeakReference {
	

	public static void main(String[] args) throws InterruptedException {
		ReferenceQueue<Bean> referenceQueue = new ReferenceQueue<>();
		Set<WeakReference<Bean>> set = new HashSet<>();
		for(int i = 0;i < 10;i ++){
			WeakReference<Bean> reference = new WeakReference<Bean>(new Bean("weak" + i),referenceQueue);
			System.out.println("just create " + reference.get());
			set.add(reference);
		}
		Bean bean = set.iterator().next().get();
		System.gc();
		
		// 等待1000ms,测试是否会回收弱引用
		Thread.sleep(1000);
		Reference<? extends Bean> reference = referenceQueue.poll();
		if(reference != null){
			System.out.println("in queue : " + reference.get());
		} else {
			System.out.println("not in queue");
		}
		System.out.println(bean);
	}

}
