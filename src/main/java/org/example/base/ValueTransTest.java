package org.example.base;

/**
 * 值传递还是引用传递
 * @date 2024/11/26
 */
public class ValueTransTest {
    public static void main(String[] args) {
        String name = "李四";
        Integer age = 18;
        setName(name);
        setAge(age);
        System.out.println(name);
        System.out.println(age);
    }
    // 复制了name的引用地址，作为实参
    public static void setName(String name) {
        // 修改实参 不影响原来的name
        name = "王五";
    }

    public static void setAge(Integer age) {
        age = 19;
    }
}
