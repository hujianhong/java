package me.huding.io.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionTest {


    public static void main(String[] args) throws IOException, InterruptedException {
//        String filePath = "http://localhost:3000";
        for(int i = 0;i < 10;i ++) {
            new Thread(() -> {
                try {
                    doGet();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        return;
    }

    private static void doGet() throws IOException, InterruptedException {
        String filePath = "http://www.baidu.com";
        URL url = new URL(filePath);
        while (true) {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setReadTimeout(20000);

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.7 Safari/537.36");

            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = inputStream.read(bytes)) != -1) {
                bos.write(bytes,0,len);
            }
            System.out.println("--------");
            System.out.println( httpURLConnection.getHeaderFields());
//            System.out.println(new String(bos.toByteArray()));
            inputStream.close();

            Thread.sleep(1000);

            httpURLConnection.disconnect();
        }
    }
}
