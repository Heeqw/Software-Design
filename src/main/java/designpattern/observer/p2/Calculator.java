package designpattern.observer.p2;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private List<ProgressListener> listeners = new ArrayList<ProgressListener>();

    public void addProgressListener(ProgressListener listener) {
        listeners.add(listener);
    }

    public void removeProgressListener(ProgressListener listener) {
        listeners.remove(listener);
    }

    float calc() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            // perform complex calculation
            Thread.sleep(1000);

            for (ProgressListener listener : listeners) {
                listener.stepPerformed(i);
            }

        }
        return 10;
    }

    public static void main(String[] args) throws InterruptedException {
        Calculator calculator = new Calculator();
        calculator.addProgressListener((step) -> System.out.println("Step: " + step));
        calculator.calc();
    }
}

interface ProgressListener {
    void stepPerformed(int step);
}
