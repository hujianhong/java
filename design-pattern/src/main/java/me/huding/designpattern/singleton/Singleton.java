package me.huding.designpattern.singleton;

/**
 * 饿汉式但单例模式
 * 
 * @author jianhonghu
 *
 */
public class Singleton {
    private static Singleton singleton = new Singleton();
    private Singleton(){}
    public static Singleton getInstance(){
        return singleton;
    }
}
