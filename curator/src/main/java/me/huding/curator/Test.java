package me.huding.curator;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created By hujianhong
 * Date: 2018/11/19
 */
public class Test {

    public static void main(String[] args) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
//        formatter.toFormat();
//        SimpleFormatter
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String string = simpleDateFormat.format(new Date(timestamp));
        System.out.println(string);

    }
}
