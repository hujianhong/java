package me.huding.common.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

public class ReaderThread {

    private final  File file;

    private final FileChannel fileChannel;

    public ReaderThread(File file) throws FileNotFoundException {
        this.file = file;
        this.fileChannel = new RandomAccessFile(file,"rw").getChannel();
    }

    public void run() throws InterruptedException, IOException {
        for(int i = 0;i < 100;i ++) {
            System.out.println(this.fileChannel.size());
            TimeUnit.SECONDS.sleep(2);
        }
        this.fileChannel.close();
    }

}
