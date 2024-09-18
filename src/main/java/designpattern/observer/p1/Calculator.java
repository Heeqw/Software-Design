package designpattern.observer.p1;

public class Calculator {

    float calc() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            // perform complex calculation
            Thread.sleep(1000);
            System.out.println("Step: " + i + "...");

        }
        return 10;
    }

    public static void main(String[] args) throws InterruptedException {
        Calculator calculator = new Calculator();
        System.out.println(calculator.calc());
    }

}
