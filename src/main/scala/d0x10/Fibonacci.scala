package d0x10

object Fibonacci extends {
  def imperative(input: Int): Int = {
    if (input <= 2)
      return 1

    var result = 0
    var prev1 = 0
    var prev2 = 1
    var i = 0
    while (i < input) {
      val tmp = result
      result += prev1 + prev2
      prev1 = prev2
      prev2 = tmp
      i += 1
    }
    result
  }
  def functional(input: Int) = ???
}