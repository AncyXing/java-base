package org.example.base;

/**
 * 字符串比较
 *
 * @date 2024/11/26
 */
public class StringTest {
    public static void main(String[] args) {
        String str1 = "abc";
        String str2 = "abc";
        System.out.println(str1 == str2);
        String str3 = "ab" + "c";
        System.out.println(str1 == str3);
        // str4无法在编译时确定，所以创建的是堆上的变量，而str1是字符串常量池中的对象
        String str4 = "ab" + new String("c");
        System.out.println(str1 == str4);
    }
}
