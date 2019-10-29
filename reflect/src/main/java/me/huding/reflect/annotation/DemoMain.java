package me.huding.reflect.annotation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DemoMain {


    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 10);
        map.put(2, 4);
        map.put(3, 22);

        List<Integer> result = map.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .map(e -> e.getKey())
                .collect(Collectors.toList());

        System.out.println(result);
    }
}
