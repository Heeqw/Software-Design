# 策略模式

# Strategy模式结构

```plantuml
@startuml

interface Strategy{
     Algorithminterface()
}
class Context{
    Contextinterface()
}
class ConcreteStrategyA{
    Algorithminterface()
}
class ConcreteStrategyB{
    Algorithminterface()
}
class ConcreteStrategyC{
    Algorithminterface()
}
Context -> Strategy
Strategy <|..ConcreteStrategyA
Strategy <|..ConcreteStrategyB
Strategy <|..ConcreteStrategyC
@enduml
```

## 需求：

```plantuml
@startuml
enum TaskType{
    continuous
    segmented
}
class Task{
    type：TaskType
    start
    end
    description
    {static} hourlyWage

    calcPayment()
}
class Action{
    start
    end 
    description
}
Task  *-> "*" Action 

@enduml
```

Task记录了任务的执行时用掉的时间。Task会一次或多次执行，每次执行都被记录在Action中。

Task这个类的最主要的接口是计算工时费(calcPayment)。

在计算Task的工时费时，有两种计算方法：
1. 按Task的开始和结束时间来计算
2. 按Task中Action的开始和结束时间的和来计算
   
## Phase1
```plantuml
@startuml
enum TaskType{
    continuous
    segmented
}
class Task{
    type：TaskType
    start
    end
    description
    {static} hourlyWage


    calcPayment()
}
note bottom : 使用对type的\nswitch/case分别计算 
class Action{
    start
    end 
    description
}
Task  *-> "*" Action 

@enduml
```

---
## Phase2 使用继承

```plantuml
@startuml
class Task{
    start
    end
    description
    {static} hourlyWage
    {abstract} calcPayment()
}
class ContinuousTask{
    calcPayment()
}
class SegmentedTask{
    calcPayment()
}
class Action{
    start
    end 
    description
}
Task <|-- ContinuousTask
Task <|-- SegmentedTask
Task  *-> "*" Action 
@enduml
```

---

## Phase3 使用策略模式

```plantuml
@startuml
class Task {
    start
    end
    {static} hourlyWage
    calcWorkTimeStrategy
    {abstract} calcPayment()
}
interface CalcWorkTimeStrategy {
    {abstract} calcWorkTime

}
class ContinuousTimeStrategy {
    calcWorkTime()
}
class SegmentedTimeStrategy {
    calcWorkTime()
}
class Action{
    start
    end 
    description
}
CalcWorkTimeStrategy <|-- ContinuousTimeStrategy
CalcWorkTimeStrategy <|-- SegmentedTimeStrategy
Task --> CalcWorkTimeStrategy
Task  *-> "*" Action 
@enduml
```

# 思考 

## 问题1：

增加一种分类方式：时薪的计算

```plantuml
@startuml
class Task {
    start
    end
    calsHourlyWageStrategy
    calcWorkTimeStrategy
    {abstract} calcPayment()
}

interface HourlyWageCalcStrategy {
    {abstract} calcHourlyWage()
}
interface TimeCalcStrategy {
    {abstract} calcWorkTime

}
class ContinuousTimeStrategy {
    calcWorkTime()
}
class SegmentedTimeStrategy {
    calcWorkTime()
}
class Action{
    start
    end 
    description
}
TimeCalcStrategy <|-- ContinuousTimeStrategy
TimeCalcStrategy <|-- SegmentedTimeStrategy
Task --> TimeCalcStrategy
Task  *-> "*" Action 

Task -->  HourlyWageCalcStrategy
@enduml
```


## 问题2：

如果确定一个任务究竟是segmented还是continuous取决于更大的上下文，比如组织机构的性质，或者是一个可配置的系统的参数，而不是在构造任务的时候能够方便地确定的，应该如何设计？







