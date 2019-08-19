package me.huding.common.future;

public class CountTask {
    private String taskId;
    public CountTask(String taskId) {
        this.taskId = taskId;
    }

    //计算分数
    public int count(String taskId){
        //执行一个长时间的任务
        return Integer.parseInt(taskId);

    }
}