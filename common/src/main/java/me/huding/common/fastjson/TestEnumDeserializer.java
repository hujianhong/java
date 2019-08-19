package me.huding.common.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

@SuppressWarnings("unchecked")
public class TestEnumDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String testEnumString = parser.parseObject(String.class);
        System.out.println("----------------");
        System.out.println(testEnumString);
        System.out.println(type);
        System.out.println(fieldName);
        System.out.println("----------------");
        return (T) TestEnum.fromName(testEnumString);
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}