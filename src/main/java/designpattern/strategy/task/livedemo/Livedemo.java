package designpattern.strategy.task.livedemo;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;

public class Livedemo{
    public static void main(String[] args) {
        Task task1 = new Task(
                ContinuousTimeStrategy.getInstance(),
                LocalDateTime.now().plusHours(-2),
                LocalDateTime.now(),
                "task 1");

        Task task2 = new Task(
                SegmentedTimeStrategy.getInstance(),
                LocalDateTime.now().plusHours(-10),
                LocalDateTime.now(),
                "task 2");

        task2.addAction(
                new Action(
                        LocalDateTime.now().plusHours(-1),
                        LocalDateTime.now(),
                        "action 1"));

        task2.addAction(
                new Action(
                        LocalDateTime.now().plusHours(-5),
                        LocalDateTime.now(),
                        "action 2"));

        System.out.println(task1.calcPayment());
        System.out.println(task2.calcPayment());
    }

}


interface CalcWorkTimeStrategy {
    float calcWorkTime(Task task);
}

class ContinuousTimeStrategy implements CalcWorkTimeStrategy {

    // 单实例
    private static final ContinuousTimeStrategy instance = new ContinuousTimeStrategy();

    public static ContinuousTimeStrategy getInstance() {
        return instance;
    }

    public float calcWorkTime(Task task) {
        return task.getDuration();
    }
}

class SegmentedTimeStrategy implements CalcWorkTimeStrategy {

    // 单实例

    private static final SegmentedTimeStrategy instance = new SegmentedTimeStrategy();

    public static SegmentedTimeStrategy getInstance() {
        return instance;
    }

    public float calcWorkTime(Task task) {
        return task.getActions().stream().map(Action::getDuration).reduce(0f, Float::sum);
    }
}

class Task {
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;
    
    private CalcWorkTimeStrategy calcWorkTimeStrategy;

    private ArrayList<Action> actions = new ArrayList<Action>();

    static float hourlyWage = 50;

    public Task(CalcWorkTimeStrategy calcWorkTimeStrategy, LocalDateTime start, LocalDateTime end, String description) {
        this.start = start;
        this.end = end;
        this.description = description;
        this.calcWorkTimeStrategy = calcWorkTimeStrategy;
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public String getDescription() {
        return description;
    }

    public float getDuration() {
        return Duration.between(start, end).toMinutes() / 60f;
    }

    float calcPayment() {

        return calcWorkTimeStrategy.calcWorkTime(this) * hourlyWage;
    }
}

class Action {
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;

    public Action(LocalDateTime start, LocalDateTime end, String description) {
        this.start = start;
        this.end = end;
        this.description = description;
    }

    /** 计算start与end之间以小时为单位的时间差 */
    public float getDuration() {
        return Duration.between(start, end).toMinutes() / 60f;
    }

    public String getDescription() {
        return description;
    }
}