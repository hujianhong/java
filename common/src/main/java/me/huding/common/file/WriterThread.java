package me.huding.common.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class WriterThread {

    private File file;

    private RandomAccessFile accessFile;

    public WriterThread(File file) throws FileNotFoundException {
        this.file = file;
        this.accessFile = new RandomAccessFile(file,"rw");
    }

    public void run() throws InterruptedException, IOException {
        for(int i = 0;i < 100;i ++) {
            this.accessFile.write(("test-test" + i).getBytes());
            TimeUnit.SECONDS.sleep(2);
        }
        this.accessFile.close();
    }

    public static void main(String[] args) throws IOException {
        Path path = Files.createTempFile("","");

        WriterThread writerThread = new WriterThread(path.toFile());
        ReaderThread readerThread = new ReaderThread(path.toFile());

        new Thread(() -> {
            try {
                writerThread.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                readerThread.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
