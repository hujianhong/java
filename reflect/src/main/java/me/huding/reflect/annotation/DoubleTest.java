package me.huding.reflect.annotation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.hitsdb.client.value.request.Point;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created By hujianhong
 * Date: 2018/11/30
 */
public class DoubleTest {

    static class NPoint extends Point {

        @Override
        public String toJSON() {
            System.out.println("override");
            return JSON.toJSONString(this,new ValueFilter(){

                @Override
                public Object process(Object o, String name, Object value) {
                    System.out.println(value);
                    if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
                        return new BigDecimal(value.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                    return value;
                }
            });
        }
    }

    public static void main(String[] args) {
        System.out.println(Point.metric("haha").tag("k","v").value(new Date(),new Double(1.0)).build().toJSON());
        System.out.println(new BigDecimal(1.0).toString());
        System.out.println(JSON.toJSONString(new BigDecimal(1.0),new ValueFilter(){

            @Override
            public Object process(Object o, String name, Object value) {
                System.out.println(value);
                if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
                    if(!value.toString().endsWith(".0")) {
                        return value.toString() + ".0";
                    }
                }
                return value;
            }
        },new SerializerFeature[0]));

        Point point = Point.metric("haha1")
                .tag("k","v")
                .value(new Date(),1.0)
                .build();
        Number value = point.getValue();
        long s1 = System.currentTimeMillis();
//        for(int i = 0;i < 500;i ++){
            if (value instanceof BigDecimal) {
                if(!value.toString().endsWith(".0")) {
                    point.setValue(new BigDecimal(value.toString() + ".00"));
                }
            }
            if(value instanceof Double || value instanceof Float) {
                if(value.toString().endsWith(".0")) {
                    point.setValue(new BigDecimal(value.toString() + "0"));
                }
            }
//        }
        long s2 = System.currentTimeMillis();
        System.out.println(s2 - s1);
        System.out.println(point.toJSON());

        System.out.println(Point.metric("haha").tag("k","v").value(new Date(),new BigDecimal("1.0")).build().toJSON());
        System.out.println(Point.metric("haha").tag("k","v").value(new Date(),new BigDecimal("1.00")).build().toJSON());
    }
}
