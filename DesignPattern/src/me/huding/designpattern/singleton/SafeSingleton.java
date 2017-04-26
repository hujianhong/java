package me.huding.designpattern.singleton;

/**
 * 
 * 懒汉式单例模式2
 * 
 * @author jianhonghu
 *
 */
public class SafeSingleton {
	private volatile static SafeSingleton singleton;

	private SafeSingleton() {
	}

	public static SafeSingleton getInstance() {
		if (singleton == null) {
			synchronized (SafeSingleton.class) {
				if (singleton == null) {
					singleton = new SafeSingleton();
				}
			}
		}
		return singleton;
	}
}