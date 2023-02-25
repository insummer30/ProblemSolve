package me.tom.basic;

public class 테스트 {
    public static void main(String[] args) {
        testClass newClass = null;

        try {
            newClass = new testClass(1);
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }
}

class testClass {
    int num;

    public testClass(int num) {
        this.num = num;

        throw new RuntimeException();
    }
}
