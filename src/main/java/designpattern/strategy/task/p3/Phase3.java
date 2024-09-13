package designpattern.strategy.task.p3;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;

public class Phase3 {
    public static void main(String[] args) {
        Task task1 = new Task(
                LocalDateTime.now().plusHours(-2),
                LocalDateTime.now(),
                "task 1",
                new ContinuousTimeStrategy()
            );

        Task task2 = new Task(
                LocalDateTime.now().plusHours(-10),
                LocalDateTime.now(),
                "task 2",
                new SegmentedTimeStrategy()
                );

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

class Task {
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;
    private CalcWorkTimeStrategy calcWorkTimeStrategy;

    private ArrayList<Action> actions = new ArrayList<Action>();

    static float hourlyWage = 50;

    public Task( LocalDateTime start, LocalDateTime end, String description, CalcWorkTimeStrategy calcWorkTimeStrategy) {
        this.start = start;
        this.end = end;
        this.description = description;
        this.calcWorkTimeStrategy = calcWorkTimeStrategy;
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

    public float calcPayment(){
        return calcWorkTimeStrategy.calcWorkTime(this) * hourlyWage;
    }
}

interface CalcWorkTimeStrategy{
    float calcWorkTime(Task task);
}

class SegmentedTimeStrategy implements CalcWorkTimeStrategy{

    @Override
    public float calcWorkTime(Task task) {
        return task.getActions().stream().map(Action::getDuration).reduce(0f, Float::sum);
    }
}

class ContinuousTimeStrategy implements CalcWorkTimeStrategy{
    @Override
    public float calcWorkTime(Task task) {
        return Duration.between(task.getStart(), task.getEnd()).toMinutes() / 60f;
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