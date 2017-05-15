# 模版方法模式

定义：定义一个操作中算法的框架，而将一些步骤延迟到子类中，使得子类可以不改变算法的结构即可重定义该算法中的某些特定步骤。

类型：行为类模式

类图：

![template-method-pattern](template-method.jpg)

事实上，模版方法是编程中一个经常用到的模式。先来看一个例子，某日，程序员A拿到一个任务：给定一个整数数组，把数组中的数由小到大排序，然后把排序之后的结果打印出来。经过分析之后，这个任务大体上可分为两部分，排序和打印，打印功能好实现，排序就有点麻烦了。但是A有办法，先把打印功能完成，排序功能另找人做。

    abstract class AbstractSort {

        /**
         * 将数组array由小到大排序
         * @param array
         */
        protected abstract void sort(int[] array);

        public void showSortResult(int[] array){
            this.sort(array);
            System.out.print("排序结果：");
            for (int i = 0; i < array.length; i++){
                System.out.printf("%3s", array[i]);
            }
        }
    }
写完后，A找到刚毕业入职不久的同事B说：有个任务，主要逻辑我已经写好了，你把剩下的逻辑实现一下吧。于是把AbstractSort类给B，让B写实现。B拿过来一看，太简单了，10分钟搞定，代码如下：

class ConcreteSort extends AbstractSort {
    @Override
    protected void sort(int[] array){
        for(int i=0; i&lt;array.length-1; i++){
            selectSort(array, i);
        }
    }

    private void selectSort(int[] array, int index) {
        int MinValue = 32767; // 最小值变量
        int indexMin = 0; // 最小值索引变量
        int Temp; // 暂存变量
        for (int i = index; i &lt; array.length; i++) {
            if (array[i] &lt; MinValue){ // 找到最小值
                MinValue = array[i]; // 储存最小值
                indexMin = i; 
            }
        }
        Temp = array[index]; // 交换两数值
        array[index] = array[indexMin];
        array[indexMin] = Temp;
    }
}
写好后交给A，A拿来一运行：

    public class Client {
        public static int[] a = { 10, 32, 1, 9, 5, 7, 12, 0, 4, 3 }; // 预设数据数组
        public static void main(String[] args){
            AbstractSort s = new ConcreteSort();
            s.showSortResult(a);
        }
    }
运行结果：

排序结果： 0 1 3 4 5 7 9 10 12 32

模板方法模式的优点：

1. 封装不便的部分，扩展可变部分。

2. 提取公共部分代码，便于维护。

3. 行为由父类控制，子类实现。

