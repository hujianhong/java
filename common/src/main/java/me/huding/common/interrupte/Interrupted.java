package me.huding.common.interrupte;

public class Interrupted {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread sleepThread = new Thread(new SleepRunner());
		sleepThread.setDaemon(true);
		
		Thread busyThread = new Thread(new BusyRunner());
		busyThread.setDaemon(true);
		
		sleepThread.start();
		busyThread.start();
		
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		busyThread.interrupt();
		sleepThread.interrupt();
		
		System.out.println("SleepThread interrupted is :" + sleepThread.isInterrupted());
		System.out.println("BusyThread interrupted is :" + busyThread.isInterrupted());
		
		Thread.sleep(2 * 1000);
		

	}
	
	static class SleepRunner implements Runnable {
		
		public void run() {
			while(true){
				try {
					Thread.sleep( 2 * 1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	static class BusyRunner implements Runnable {
		
		public void run(){
			while(true){
				
			}
		}
	}

}
