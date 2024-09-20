# Iterator 模式

## Iterator 模式结构

提供一种方法顺序访问一个聚合对象中各个元素，而又不暴露该对象的内部表示。


```plantuml
interface Iterable{
    iterator()
}

interface Iterator{
    hasNext()
    next()
}

Iterable -> Iterator

class ConcreteIterable <<Iterable>> implements Iterable{
    iterator()
}

class ConcrtetIterator <<Iterator>> implements Iterator{
    hasNext()
    next()

}

ConcreteIterable -> ConcrtetIterator

```

## 场景 1： 棋盘的遍历

CheckBoard.java

示例中CheckBoard中实现了Iterable接口，返回一个迭代器(Iterator)。可以使用for-each循环来遍历。

## 场景 2： 循环遍历

Rotated.java

可以通过增加参数构造一个特定的迭代器，该迭代器可以以特定的方式遍历聚合(aggregate),或者遍历聚合一个特定的子集。


## 场景 3： 斐波那级数(java,python,scala)

* fib.java
* fib.py
* fib.scala

分别以函数式、生成器和接口的方式实现斐波那契数列。用于说明：

1. 不同的生成迭代器的方式
2. 函数式的Iterator的优势

## 问题 ： 基于二维棋盘的游戏(java,scala)

* CheckBoard.java
* CheckBoard.scala

注意：由于采用了函数式的功能，本例完整示例代码为scala,java示例中仅有部分功能。

有很多游戏是基于二维棋盘的，比如围棋，象棋，国际黑白棋，五子棋等。考虑两种游戏：

1. 黑白棋
2. 2048

这些游戏需要对棋盘上棋子按照某些规则进行查找，如果不进行恰当的抽象，代码很容易写成下面的样子：

代码片段1：
![alt text](images/image-1.png)


如果进行抽象，尽可能减少这类代码。

下面考虑两个案例。

## 1. 黑白棋
对于黑白棋游戏，即在一个二维的棋盘上放置黑白棋子的游戏。

1. 如何获取已经落下的所有棋子
2. 如果一颗棋子落下后，它能够在八个方向上攻击其他颜色的棋子，如果获取它可以攻击到的所有棋子。


## 方案（scala）

```plantuml
@startuml

enum PieceFace{
    black, white
}

class Cell{
    location
}
class Piece{
    face
}
class Blank

Cell <|--Piece
Cell <|--Blank

class Checkboard <<iterable>>{
    pieces
    get(location)
    iterator()
    iterator(loc, dir)
    getAttackedPiecesBy(piece)

}
Checkboard -> "*" Cell
@enduml
```

getAttackedPiecesBy 是用于获取一个落子(piece)能够在八个方向上攻击到哪些对方的棋子。

使用迭代器模式，计算的框架如下：

* 构造包含从落子位置开始的所有8个方向的集合
* 对每个方向 
    * 获取每个方向开始的迭代器
    * 获取沿该方向的第一个棋子，得到了8个棋子(可能棋子为空)
* 只留下对手棋子


## 代码(scala)

```scala
// piece为落子

Direction.values.toList // 所有方向的集合
    .map(dir =>
        //从落子开始的每个方向的迭代器
        iterator(piece.location, dir)
        //第一个非空白的棋子
        .collectFirst { 
            case p @ Piece(location, face) => p
        }
    )
    //只选取对手棋子
    .filter(_.face.isOpposite(piece.face))
```

## 2. 2048

2048是大家熟悉的游戏，游戏操作很简单，就是在一个2048*2048的棋盘上，每个格子上放置一个数字，数字的大小是2的n次方，每次操作选择一个方向，游戏按照特定的规则对该方向所有列进行一个变换。

请大家自行考虑如何使用迭代器模式来帮助实现2048游戏。