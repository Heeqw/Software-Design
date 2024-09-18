package iterator

import scala.collection.mutable.HashMap

class rotatedIterator[A](seq: List[A], start: Int) extends Iterator[A] {
  var (before, after) = seq.splitAt(start)
  def next = after match {
    case Seq()  =>
      val (h :: t) = before; before = t; h
    case h :: t => after = t; h
  }
  def hasNext = after.nonEmpty || before.nonEmpty
}

enum Direction(val x: Int, val y: Int) {
  case left extends Direction(-1, 0)
  case right extends Direction(1, 0)
  case up extends Direction(0, -1)
  case down extends Direction(0, 1)
  case upleft extends Direction(-1, -1)
  case upright extends Direction(1, -1)
  case downleft extends Direction(-1, 1)
  case downright extends Direction(1, 1)
}

enum Edge{
  case top, bottom, left, right
}

case class Location(
    x: Int,
    y: Int
) {
  def next(dir: Direction) =
    Location(x + dir.x, y + dir.y)
}

case class Box(maxX: Int, maxY: Int) {

  def iterator(
      loc: Location,
      dir: Direction
  ): Iterator[Location] = {
    new Iterator[Location] {
      var curLoc: Location = loc
      override def hasNext: Boolean =
        curLoc.x < maxX &&
          curLoc.x >= 0 &&
          curLoc.y < maxY &&
          curLoc.y >= 0
      override def next(): Location =
        val res = curLoc
        curLoc = curLoc.next(dir)
        res
    }
  }

  def edge(e: Edge) = 
    e match
      case Edge.top => iterator(
        Location(0, 0), Direction.right
      )
      case Edge.bottom => iterator(
        Location(0, maxY-1), Direction.right
      )
      case Edge.left => iterator(
        Location(0,0), Direction.down
      )
      case Edge.right => iterator(
        Location(maxX-1,0), Direction.down
      )
}

//黑白棋
package blackwhite {

  enum PieceFace {
    case white, black
    def isOppositeOf(that: PieceFace) = this != that
  }

  // 棋格
  enum Cell(location: Location) {

    case Piece(
        location: Location,
        face: PieceFace
    ) extends Cell(location)

    case Blank(
      location: Location
    ) extends Cell(location)

  }

  import Cell._

  // 棋盘 Checkboard
  case class Checkboard(
      box: Box,
      pieces: HashMap[Location, PieceFace]
  ) {

    // 1.获取指定的棋格的内容
    def get(location: Location): Cell =
      pieces
        .get(location)
        .fold(Blank(location))(Piece(location, _))

    // 2. 棋盘上棋子的迭代器
    def iterator: Iterator[Piece] =
      pieces.iterator
        .map(Piece(_, _).asInstanceOf[Piece])
        .toList
        .sortBy(c => c.location.x * c.location.y + c.location.x)
        .iterator

    // 3.从自定位子开始的指定方向的迭代器
    def iterator(loc: Location, dir: Direction): Iterator[Cell] =
      box.iterator(loc, dir).map(get)

    // 获取piece能够攻击到的所有棋子
    def attackedPiecesBy(piece: Piece) =
      // 所有方向
      Direction.values.toList
        .map(dir =>
          //从落子开始的每个方向的迭代器
          iterator(piece.location, dir)
            //第一个非空白的棋子
            .collectFirst { 
              case p @ Piece(_, _) => p
            }
        )
        //有些方向没有棋子，需要过滤掉
        .collect { 
          case p @ Some(value) => value
        }
        //只选取对手棋子
        .filter(_.face.isOppositeOf(piece.face))


  }
}

// 2048
package _2048{

  def shiftLine(line: List[Cell]) : List[Cell] = 
    line
      .map(_.location)
      .zip(rotatedIterator(line.map(_.face),line.size - 1))
      .map(Cell.apply)

  enum MoveAction(val edge: Edge, val dir: Direction){
    case up extends MoveAction(Edge.bottom, Direction.up)
    case down extends MoveAction(Edge.top, Direction.down)
    case left extends MoveAction(Edge.right, Direction.left)
    case right extends MoveAction(Edge.left, Direction.right)
  }

  case class Face(value: Int)

  given Conversion[Int, Face] with
    override def apply(x: Int): Face = Face(x)

  case class Cell(location: Location, face: Face)
  case class Checkboard(
    box: Box,
    values: Array[Array[Face]]
  ){
    def get(location: Location) : Cell =
      Cell(location,values(location.y)(location.x))

    def set(cell: Cell) =
      values(cell.location.y)(cell.location.x) = cell.face

    def iterator(loc: Location, dir: Direction) =
      box.iterator(loc, dir).map(get)

    def shift(action: MoveAction) = {
      box.edge(action.edge)
        .map(loc=>iterator(loc, action.dir).toList)
        .map(shiftLine)
        .foreach(_.foreach(p =>set(p)))
    }
    def show = values.map(_.mkString(",")).mkString("\n")
  }
}
