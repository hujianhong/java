package me.huding.hunter.bean;
public class Property {

    private String name;
    //使用value属性直接指定值，也可以使用ref属性来指定依赖的对象
    private String value;
    private String ref;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getRef() {
        return ref;
    }
    public void setRef(String ref) {
        this.ref = ref;
    }


}