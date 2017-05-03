package d0x10

import scala.annotation.tailrec

object Fibonacci extends {
  def imperative(input: Int): BigInt = {
    var a = 0
    var b = 1
    var i = 0
   
    while (i < input) {
      val tmp = a + b
      a = b
      b = tmp
      i = i + 1
    } 
    a
  }

  def functional(input: Int) = {
    @tailrec
    def iter(n: Int, a: BigInt, b: BigInt): BigInt = n match {
      case 0 => a
      case 1 => b
      case _ => iter(n - 1, b, a + b)
    }
    iter(input, 0, 1)
  }
}