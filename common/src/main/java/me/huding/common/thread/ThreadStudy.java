package me.huding.common.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadStudy {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		Future<Boolean> future = executorService.submit(() -> {
			int i = 0;
			while (i < 100000000){
				System.out.println(i ++);
			}
			return true;
		});
		future.cancel(true);
		System.out.println("-------------");
	}

}
