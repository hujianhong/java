#装饰器模式

装饰器模式：动态给一个对象添加一些额外的职责。就增加功能来说，装饰器模式比生成子类更为灵活。


类图：

![](decorator.gif)




装饰器模式有以下4个角色：

1.抽象构件（component）角色：该角色用于规范需要装饰的对象（原始对象）；

2.具体构件（Concrete Component）角色：该角色实现抽象构件接口，定义一个需要装饰的原始类。

3.装饰（Decorator）角色：该角色持有一个构件对象的实例，并定义一个与抽象构件接口一致的接口；

4.具体装饰（Concrete Decorator）角色，该角色负责对构件对象进行装饰。


装饰模式的优缺点：

1.装饰类和被装饰类可以独立发展，而不会互相耦合。

2.装饰模式使继承关系的一个替代方案。

3.装饰器模式可以动态扩展一个实现类的功能。



# 装饰器模式与代理模式的区别

从功能效果上看
　　装饰模式：在不改变接口的前提下，动态扩展对象的功能

　　代理模式：在不改变接口的前提下，控制对象的访问

　　装饰模式强调功能扩展，比如A对象的B方法，运用装饰模式后，在调用B方法前后，实现新的功能，此时B方法效果与原来不同

　　代理模式强调控制访问，如上例，运用代理模式后，在调用B方法前后，控制怎么访问B方法的原始数据，而对于B实现的功能效果不做修改

　　因此，如果运用设计模式后，方法的功能效果（主要是输出效果）不变，一般可视为代理。

　从类结构上看
　　通过装饰模式结构图中可以看出

![](9297010439.gif)

Component类在Decorator模式中充当抽象接口的角色，不应该去实现具体的行为。而且Decorator类对于Component类应该透明，换言之Component类无需知道Decorator类，Decorator类是从外部来扩展Component类的功能。
　　Decorator类在接口上表现为is-a Component的继承关系，即Decorator类继承了Component类所具有的接口。但在实现上又表现为has-a Component的组合关系


同样，通过代理模式结构图中可以得出


![](9298010439.gif)

代理类和被代理对象是has-a关系，一般没有is-a关系，除非代理类直接继承被代理类，重写被代理类的方法，即上图中没有抽象Subject类时的情况。


参考资料：1.[http://www.educity.cn/wenda/363782.html](http://www.educity.cn/wenda/363782.html)


#适配器模式与装饰器模式的区别：

装饰器与适配器都有一个别名就是包装模式，他们看起来都是起到包装一个类或对象的作用，但是使用它们的目的不一样。适配器模式的意义是要将一个接口转变成另外一个接口，它的目的是通过改变接口来达到重复使用的目的；而装饰器模式不是要改变被装饰对象的接口，而是恰钱保持原有的接口，但是增强原有对象的功能，或者改变原有对象的处理方法而提升性能。所以这两个设计模式的目的是不同。