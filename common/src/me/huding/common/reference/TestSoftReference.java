package me.huding.common.reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Set;

public class TestSoftReference {
	

	/**
	 * 没有被回收
	 * @param args
	 */
	public static void main(String[] args) {
		ReferenceQueue<Bean> referenceQueue = new ReferenceQueue<>();
		Set<SoftReference<Bean>> set = new HashSet<>();
		for(int i = 0;i < 10;i ++){
			SoftReference<Bean> reference = new SoftReference<Bean>(new Bean("soft" + i),referenceQueue);
			System.out.println("just create" + reference.get());
			set.add(reference);
		}
		System.gc();
		
		
		Reference<? extends Bean> reference = referenceQueue.poll();
		if(reference != null){
			System.out.println( "in queue : " + reference.get());
		} else {
			System.out.println("not in queue");
		}
	}

}
