package designpattern.strategy.task.p1;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;

public class Phase1 {
    public static void main(String[] args) {
        Task task1 = new Task(
                TaskType.continuous,
                LocalDateTime.now().plusHours(-2),
                LocalDateTime.now(),
                "task 1");

        Task task2 = new Task(
                TaskType.segmented,
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
    continuous, segmented
}

class Task {
    private TaskType type;
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;

    private ArrayList<Action> actions = new ArrayList<Action>();

    static float hourlyWage = 50;

    public Task(TaskType type, LocalDateTime start, LocalDateTime end, String description) {
        this.type = type;
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

    float calcPayment() {
    
        float hours = 0;

        switch(type){
            case continuous:
                hours = Duration.between(start, end).toMinutes() / 60f;
                break;
            case segmented:
                hours = actions.stream().map(Action::getDuration).reduce(0f, Float::sum);
                break;
        }

        return hours * hourlyWage;
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