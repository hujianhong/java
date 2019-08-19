package me.huding.common.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;

public class TaskExecutor {
    private static Logger log = LoggerFactory.getLogger(TaskExecutor.class);
    //当前在执行的任务列表
    private Map<String, Future> taskMap;
    //执行任务的线程池
    private ExecutorService executorService;


    private TaskExecutor() {
        this.taskMap = new ConcurrentHashMap<>();
        this.executorService = Executors.newFixedThreadPool(16);
    }

    /**
     * 单例
     */
    private static class ExecutorInstance {
        private static final TaskExecutor instance = new TaskExecutor();
    }

    public static TaskExecutor getInstance() {
        return ExecutorInstance.instance;
    }

    //执行任务
    public Boolean runTask(String taskId) {
        Future<Integer> future;
        try {
            synchronized (this) {
                //如果任务存在
                if (isExistTask(taskId)) {
                    return false;
                }
                //创建计算任务
                CountTask countTask = new CountTask(taskId);
                //提交任务
                future = executorService.submit(() -> countTask.count(taskId));
                //将任务放入任务列表
                taskMap.put(taskId, future);
                log.info("run task {} ", taskId);
            }
            //获取任务结果
            Integer result = future.get();
            log.info("result {}", result);
            taskMap.remove(taskId);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    //中止任务
    public boolean cancleTask(String taskId) {
        if (!taskMap.containsKey(taskId)) {
            return false;
        }
        Future<Integer> future = taskMap.get(taskId);
        future.cancel(true);
        taskMap.remove(taskId);
        log.info("remove task {}", taskId);
        return false;
    }

    //判断任务是否存在
    public boolean isExistTask(String taskId) {
        if (taskMap.containsKey(taskId)) {
            return true;
        }
        return false;
    }
}