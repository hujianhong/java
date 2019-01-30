package me.huding.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By hujianhong
 * Date: 2018/11/21
 */
public class NumberTest {

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        list.add(1289);
        list.add(Long.MAX_VALUE - 100);

        for(Object object : list){
            System.out.println(((Number)object).longValue());
        }
    }
}
