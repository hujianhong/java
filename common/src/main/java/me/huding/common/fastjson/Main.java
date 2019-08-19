package me.huding.common.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

public class Main {

    public static void main(String[] args) {
        ParserConfig.getGlobalInstance().putDeserializer(TestEnum.class, new TestEnumDeserializer());


        String string = JSON.toJSONString(TestEnum.TEST);

        System.out.println(string);

        TestEnum testEnum = JSON.parseObject(string, TestEnum.class);

        System.out.println(testEnum);
    }
}
