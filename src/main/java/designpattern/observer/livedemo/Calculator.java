package designpattern.observer.livedemo;

import java.util.List;
import java.util.ArrayList;

/**
 * InnerCalculator
 */
interface CalcProgressListener {
    void stepPreformed(int progress);
}

class StepListener implements CalcProgressListener {

    @Override
    public void stepPreformed(int progress) {
        System.out.println("Step: " + progress + "...");
    }

}

public class Calculator {

    List<CalcProgressListener> listeners = new ArrayList<>();

    public void addCalcProgressListener(CalcProgressListener listener) {
        listeners.add(listener);
    }

    float calc() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            // perform complex calculation
            Thread.sleep(1000);
            for (CalcProgressListener listener : listeners) {
                listener.stepPreformed(i);
            }

        }
        return 10;
    }

    public static void main(String[] args) throws InterruptedException {
        Calculator calculator = new Calculator();
        calculator.addCalcProgressListener(new StepListener());
        calculator.calc();
    }

}
