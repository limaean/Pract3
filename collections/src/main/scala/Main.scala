object Main extends App {

  def bigint(): Unit = {
    val bigInt = BigInt(678365835)
    val sumTuple = bigInt /% 37
    val (quotient, remainder) = sumTuple
    println(s"quotient is $quotient, remainder is $remainder")
}
  //bigint()

  def char(): Unit = {
    val x: Char = '2'
    val result = x match {
      case '+' => 1
      case '-' => -1
      case _ => 0
    }
    println(result)
  }
  //char()
  def factor(): Unit = {
    val factorArray: Array[(Int, Int)] = new Array[(Int, Int)](43)
    var k = 0
    for (i <- 1 to 100)
      for (j <- 1 to 100)
        if (i + j == 48 && i % 10 + j % 10 == 8) {
          factorArray(k) = (i, j)
          k += 1
        }
    println(factorArray.mkString(", "), factorArray.length)
  }
  factor()
  def eqSum(): Unit = {

  }

}