package com;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xinput
 * @Date: 2020-04-10 18:59
 */
public class Case01 {
    public static void main(String[] args) {
        List<String> aa = new ArrayList(2);
        aa.add("1");
        aa.add("2");
        for (int i = 0; i < aa.size(); i++) {
            System.out.println(aa.get(i));
        }
        aa.set(0, "3333");
        for (int i = 0; i < aa.size(); i++) {
            System.out.println(aa.get(i));
        }

    }
}
