package me.huding.common.future;

public class Main {

    public static void main(String[] args) {
        TaskExecutor taskExecutor = TaskExecutor.getInstance();

        System.out.println(taskExecutor.runTask("123"));

    }
}
