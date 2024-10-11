

public class Test {

    public void method1(int x) {
        if (x > 0) {
            System.out.println("x is positive");
        } else {
            System.out.println("x is not positive");
        }
    }

    public void method2(int x, int y) {
        if (x > 0) {
            if (y > 0) {
                System.out.println("Both x and y are positive");
            } else {
                System.out.println("x is positive but y is not");
            }
        } else {
            System.out.println("x is not positive");
        }
    }

    public int method3(int a, int b) {
        if (a > 0 && b > 0) {
            return a + b;
        } else if (a < 0 && b < 0) {
            return a - b;
        } else {
            return 0;
        }
    }
}
