package me.huding.io.http;


import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoServer {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Queue<UploadTask> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 10; i++) {
            UploadRunner uploadRunner = new UploadRunner("", 824, queue);
            executorService.submit(() -> uploadRunner);
        }
        // offer task
        queue.offer(new UploadTask("hujianhong"));
    }

    public static class  UploadTask {
        //
        String target;

        public UploadTask(String target) {

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
            // TODO upload
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
