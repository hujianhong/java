package me.huding.designpattern.singleton;
/**
 * 
 * 懒汉式单例模式
 * 
 * @author jianhonghu
 *
 */
public class Singleton1 {
        private static Singleton1 singleton;
        private Singleton1(){}

        public static synchronized Singleton1 getInstance(){
            if(singleton==null){
                singleton = new Singleton1();
            }
            return singleton;
        }
    }