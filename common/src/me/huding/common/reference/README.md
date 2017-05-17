# Java引用


各种级别引用：强引用、软引用、弱引用、虚引用

1、强引用 

如果一个对象具有强引用，GC绝不会回收它；当内存空间不足，JVM宁愿抛出OutOfMemoryError错误。一般new出来的对象都是强引用。


2、软引用

如果一个对象具有软引用，当内存空间不足，GC会回收这些对象的内存，使用软引用构建敏感数据的缓存。


软引用的声明的借助强引用或者匿名对象，使用泛型SoftReference<T>；可以通过get方法获得强引用。具体如下：

```

SoftReference<User>softReference=new SoftReference<User>(new User());  
strangeReference=softReference.get();//通过get方法获得强引用

```

3、弱引用  

如果一个对象具有弱引用，在GC线程扫描内存区域的过程中，不管当前内存空间足够与否，都会回收内存，利用jdk中的ThreadLocal就是弱引用的，具体间下面的详细说明。

```
WeakReference<User>weakReference=new WeakReference<User>(new User());

```


4、虚引用

如果一个对象仅持有虚引用，在任何时候都可能被垃圾回收，虚引用与软引用和弱引用的一个区别在于：虚引用必须和引用队列联合使用，虚引用主要用来跟踪对象被垃圾回收的活动。

```
PhantomReference<User> phantomReference=new PhantomReference<User>(new User(),new ReferenceQueue<User>());
```



Java的引用对象类在包java.lang.ref下。

其中包含了三种显式的引用类型（也即是Reference类的三个子类）：

　　SoftReference

　　WeakReference

　　PhantomReference

　　一个引用对象（reference object）（即以上三种引用类型的对象）封装了一个对其他对象的引用（称作referent）。引用对象提供了对referent的clean和get操作，但是不提供set操作。引用对象本身可以像其他一般的对象一样被检查和操纵。三种类型的引用定义了三种不同层次的可达性级别，由强到弱排列如下：

　　SoftReference > WeakReference > PhantomReference

　　越弱表示对垃圾回收器的限制越少，对象越容易被回收。

## SoftReference
　　SoftReference用来实现一些内存敏感的缓存(Soft references are for implementing memory-sensitive caches)，只要内存空间足够，对象就会保持不被回收。反之，当宿主进程的内存空间不足时，对象就会被GC回收。

　　所以SoftReference意味着：hold on until you can’t.

## WeakReference
　　WeakReference可以用来实现一些规范化映射（WeakHashMap），其中key或者value当它们不再被引用时可以自动被回收。当你想引用一个对象，但是这个对象有自己的生命周期，你不想介入这个对象的生命周期，这时候你就是用弱引用。这个引用不会在对象的垃圾回收判断中产生任何附加的影响。

## PlantomReference
　　PlantomReference和WeakReference一样，也不会介入引用对象的生命周期。

　　PhantomReference用来调度一些预验清理动作，提供比Java清理机制更灵活的处理方式。（Phantom references are for scheduling pre-mortem cleanup actions in a more flexible way than is possible with the Java finalization mechanism.）

　　PlantomReference比较特殊，它的get方法总是返回null，所以你得不到它引用的对象。它保存ReferenceQueue中的轨迹。它允许你知道对象何时从内存中移除。


参考资料：

1. [Java强引用、软引用、弱引用、虚引用详解](http://blog.csdn.net/xiaofengcanyuexj/article/details/45271195)

2. [Java中的引用类型（强引用、弱引用）和垃圾回收](http://www.cnblogs.com/mengdd/archive/2013/09/03/3298852.html)

