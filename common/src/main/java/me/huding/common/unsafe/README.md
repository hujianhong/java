通常我们最好也不要使用Unsafe类，除非有明确的目的，并且也要对它有深入的了解才行。要想使用Unsafe类需要用一些比较tricky的办法。Unsafe类使用了单例模式，需要通过一个静态方法getUnsafe()来获取。但Unsafe类做了限制，如果是普通的调用的话，它会抛出一个SecurityException异常；只有由主类加载器加载的类才能调用这个方法。其源码如下：

复制代码
1 public static Unsafe getUnsafe() {
2     Class var0 = Reflection.getCallerClass();
3     if(!VM.isSystemDomainLoader(var0.getClassLoader())) {
4         throw new SecurityException("Unsafe");
5     } else {
6         return theUnsafe;
7     }
8 }
复制代码
 

网上也有一些办法来用主类加载器加载用户代码，比如设置bootclasspath参数。但更简单方法是利用Java反射，方法如下：

1 Field f = Unsafe.class.getDeclaredField("theUnsafe");
2 f.setAccessible(true);
3 Unsafe unsafe = (Unsafe) f.get(null);
 

获取到Unsafe实例之后，我们就可以为所欲为了。Unsafe类提供了以下这些功能：

一、内存管理。包括分配内存、释放内存等。

该部分包括了allocateMemory（分配内存）、reallocateMemory（重新分配内存）、copyMemory（拷贝内存）、freeMemory（释放内存 ）、getAddress（获取内存地址）、addressSize、pageSize、getInt（获取内存地址指向的整数）、getIntVolatile（获取内存地址指向的整数，并支持volatile语义）、putInt（将整数写入指定内存地址）、putIntVolatile（将整数写入指定内存地址，并支持volatile语义）、putOrderedInt（将整数写入指定内存地址、有序或者有延迟的方法）等方法。getXXX和putXXX包含了各种基本类型的操作。

利用copyMemory方法，我们可以实现一个通用的对象拷贝方法，无需再对每一个对象都实现clone方法，当然这通用的方法只能做到对象浅拷贝。

二、非常规的对象实例化。

allocateInstance()方法提供了另一种创建实例的途径。通常我们可以用new或者反射来实例化对象，使用allocateInstance()方法可以直接生成对象实例，且无需调用构造方法和其它初始化方法。

这在对象反序列化的时候会很有用，能够重建和设置final字段，而不需要调用构造方法。

三、操作类、对象、变量。

这部分包括了staticFieldOffset（静态域偏移）、defineClass（定义类）、defineAnonymousClass（定义匿名类）、ensureClassInitialized（确保类初始化）、objectFieldOffset（对象域偏移）等方法。

通过这些方法我们可以获取对象的指针，通过对指针进行偏移，我们不仅可以直接修改指针指向的数据（即使它们是私有的），甚至可以找到JVM已经认定为垃圾、可以进行回收的对象。

四、数组操作。

这部分包括了arrayBaseOffset（获取数组第一个元素的偏移地址）、arrayIndexScale（获取数组中元素的增量地址）等方法。arrayBaseOffset与arrayIndexScale配合起来使用，就可以定位数组中每个元素在内存中的位置。

由于Java的数组最大值为Integer.MAX_VALUE，使用Unsafe类的内存分配方法可以实现超大数组。实际上这样的数据就可以认为是C数组，因此需要注意在合适的时间释放内存。

五、多线程同步。包括锁机制、CAS操作等。

这部分包括了monitorEnter、tryMonitorEnter、monitorExit、compareAndSwapInt、compareAndSwap等方法。

其中monitorEnter、tryMonitorEnter、monitorExit已经被标记为deprecated，不建议使用。

Unsafe类的CAS操作可能是用的最多的，它为Java的锁机制提供了一种新的解决办法，比如AtomicInteger等类都是通过该方法来实现的。compareAndSwap方法是原子的，可以避免繁重的锁机制，提高代码效率。这是一种乐观锁，通常认为在大部分情况下不出现竞态条件，如果操作失败，会不断重试直到成功。

六、挂起与恢复。

这部分包括了park、unpark等方法。

将一个线程进行挂起是通过park方法实现的，调用 park后，线程将一直阻塞直到超时或者中断等条件出现。unpark可以终止一个挂起的线程，使其恢复正常。整个并发框架中对线程的挂起操作被封装在 LockSupport类中，LockSupport类中有各种版本pack方法，但最终都调用了Unsafe.park()方法。

七、内存屏障。

这部分包括了loadFence、storeFence、fullFence等方法。这是在Java 8新引入的，用于定义内存屏障，避免代码重排序。

loadFence() 表示该方法之前的所有load操作在内存屏障之前完成。同理storeFence()表示该方法之前的所有store操作在内存屏障之前完成。fullFence()表示该方法之前的所有load、store操作在内存屏障之前完成。



参考资料：

1. [http://www.cnblogs.com/pkufork/p/java_unsafe.html](http://www.cnblogs.com/pkufork/p/java_unsafe.html)

2. [http://ifeve.com/sun-misc-unsafe/](http://ifeve.com/sun-misc-unsafe/)