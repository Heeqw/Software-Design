@main
def test() = {

  //斐波那级数的迭代器
  val fib = Iterator
    .iterate((0, 1))(x => (x._2, x._1 + x._2))
    .map(_._2)

  println(fib.filter(_ % 2 == 0).take(10).mkString(","))
}
