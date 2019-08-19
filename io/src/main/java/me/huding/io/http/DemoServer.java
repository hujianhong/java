package me.huding.io.http;


import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoServer {

    private ExecutorService executorService;

    private Queue<UploadTask> queue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws IOException {
        DemoServer demoServer = new DemoServer();


        CompletableFuture<String> future = demoServer.offer("hujianhong");
        future.whenComplete((r, ex) -> {
            if (ex != null) {
                // TODO 有异常，处理上传失败问题

            } else {
                // 上传成功，r就是上传任务返回的结果url

                String resultUrl = r;
                // TODO 做相应的处理，比如给客户端返回上传结果等等

            }
        });
    }


    public DemoServer() throws IOException {
        this.executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            UploadRunner uploadRunner = new UploadRunner("", 824, queue);
            executorService.submit(() -> uploadRunner);
        }
    }

    public CompletableFuture<String> offer(String target) {
        CompletableFuture<String> future = new CompletableFuture<>();
        // offer task
        queue.offer(new UploadTask("hujianhong", future));
        return future;
    }

    public static class  UploadTask {
        //
        private final String target;

        private final CompletableFuture<String> future;

        public UploadTask(String target, CompletableFuture<String> future) {
            this.target = target;
            this.future = future;
        }

        public String getTarget() {
            return target;
        }

        public CompletableFuture<String> getFuture() {
            return future;
        }
    }

    public static class UploadRunner implements Runnable {
        Socket socket;

        Queue<UploadTask> queue;

        private String token;

        private volatile boolean isStop;

        UploadRunner(String ip, int port, Queue<UploadTask> queue) throws IOException {
            socket = new Socket(ip, port);
            this.queue = queue;
        }

        @Override
        public void run() {
            while (!isStop) {
                try {
                    UploadTask task = queue.poll();
                    checkOrUpdateToken();
                    upload(task);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void upload(UploadTask task) {
            CompletableFuture<String> future = task.getFuture();
            try {

                // TODO upload

                // 设置返回结果
                String resultUrl = "";
                future.complete(resultUrl);
            } catch (Exception e) {
                // 上传失败，
                future.completeExceptionally(e);
            }
        }

        public void checkOrUpdateToken() {
            // TODO check token is valid or refresh token from server?
        }


        public void close() throws IOException {
            this.isStop = true;
            this.socket.close();
        }
    }
}
