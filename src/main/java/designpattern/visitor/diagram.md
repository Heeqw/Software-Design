```plantuml
@startuml
interface Element #white{
    {abstract} accept(visitor)
}
interface Visitor #white{
    {abstract} visit(Element)
}

interface ASTNode #white{

} 
interface ASTNode #white{
    {abstract} accept(visitor)

}

interface ASTVisitor #white{
    {abstract} visit(CompilationUnit)
    {abstract} visit(ClassDeclaration)
    ...()
}
@enduml
```

```plantuml
@startuml
interface Visitor #white{
    {abstract} visit(Element)
} 
interface Element #white{
    {abstract} accept(visitor)
}
Visitor . Element

class ConcreteElement1 {
    accept()
}
class ConcreteElement2{
    accept()
}
Element <|-- ConcreteElement1
Element <|-- ConcreteElement2

class ConcreteVisitor1 {
    visit()
}
class ConcreteVisitor2 {
    visit()
}
Visitor <|-- ConcreteVisitor1
Visitor <|-- ConcreteVisitor2
@enduml
```
---

```plantuml
@startuml
class Computer
class Chassis
class Monitor
class Harddisk
class Switch{
    close()
}

Computer *--> Chassis
Computer *--> Monitor
Chassis *--> Switch
Chassis *--> Harddisk
Monitor *--> Switch



@enduml
```

```typescript
//未使用
computer.monitor.switch.close()
computer.chassis.switch.close()

class SwitchClose extend PartVisitorImpl{
    visit(sw : Switch){
        sw.close()
    }
}
computer.accept(new SwitchClose());
```

```plantuml
@startuml

interface Part <<Element>>{
    accept(visitor)
}
interface PartVisitor<<Visitor>>{
    visitComputer(element)
    visitMonitor(element)
    visitChassis(element)
    visitChassis(element)
    visitSwitch(element)
}

Part ..PartVisitor

class Computer <<ConcreteElement>>{
}
class Chassis <<ConcreteElement>>{
}
class Monitor <<ConcreteElement>>{
}
class Harddisk <<ConcreteElement>>{
}
class Switch <<ConcreteElement>>{
    close()
}

class SwitchClose<<ConcreteVisitor>> #LightYellow {
   visitSwitch(element) 
}
PartVisitor <|-- SwitchClose
Computer .|> Part
Chassis ..|> Part
Monitor ..|> Part
Harddisk ..|> Part
Part <|. Switch 
@enduml
```
