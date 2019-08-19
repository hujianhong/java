package me.huding.common.fastjson;

public enum TestEnum {
    /**
     * Various
     */
    TEST("test"),HH("hh");

    private String name;


    private TestEnum(String value){
        this.name = value;
    }

    public static TestEnum fromName(String name) {
        switch (name) {
            case "test":
                return TEST;
            case "hh":
                return HH;
        }
        return TEST;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "TestEnum{" +
                "name='" + name + '\'' +
                '}';
    }
}