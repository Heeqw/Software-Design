package designpattern.strategy.task.p2;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;

public class Phase2 {
    public static void main(String[] args) {
        Task task1 = new ContinuousTask(
                LocalDateTime.now().plusHours(-2),
                LocalDateTime.now(),
                "task 1");

        Task task2 = new SegmentedTask(
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

enum TaskType {
    continueing, segmented
}

abstract class Task {
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;

    private ArrayList<Action> actions = new ArrayList<Action>();

    static float hourlyWage = 50;

    public Task( LocalDateTime start, LocalDateTime end, String description) {
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    abstract float calcPayment();
}

class ContinuousTask extends Task {
    public ContinuousTask(
            LocalDateTime start,
            LocalDateTime end,
            String description) {
        super(start, end, description);
    }

    @Override
    float calcPayment() {
        float hours = Duration.between(getStart(), getEnd()).toMinutes() / 60f;
        return hours * hourlyWage;
    }
}

class SegmentedTask extends Task {

    public SegmentedTask(
            LocalDateTime start,
            LocalDateTime end,
            String description) {
        super( start, end, description);
    }

    @Override
    float calcPayment() {
        return getActions().stream().map(Action::getDuration).reduce(0f, Float::sum) * hourlyWage;
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