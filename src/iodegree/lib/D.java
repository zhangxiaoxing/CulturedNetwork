/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib;

/**
 *
 * @author Libra
 */
public class D {

    public static <T> void p(T... s) {
        tp(s);
    }

    public static <T> void tp(T... s) {
        if (s.length < 1) {
        } else {
            for (T str : s) {
                System.out.print(str);
                System.out.print("\t");
            }
        }
        System.out.println();
    }

    public static <T> void tpi(T... s) {
        for (T str : s) {
            System.out.print(str);
            System.out.print("\t");
        }
    }
//
//    public static void tpi(String s) {
//        System.out.print(s + ",");
//    }

//    public static void tp(int s) {
//        System.out.println(s);
//    }
//
//    public static void tp(float f) {
//        System.out.println(f);
//    }
//    public static void tpi(float f) {
//        System.out.print(f + ",");
//    }
//    public static void tp(double s) {
//        System.out.println(s);
//    }
//    public static void tpi(int s) {
//        System.out.print(s + ",");
//    }
    public static void hit() {
        System.out.println("hit");
    }

    public static void hit(int i) {
        System.out.println("hit" + i);
    }
}
