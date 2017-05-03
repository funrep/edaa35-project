package d0x10

import scala.io.Source
import java.io.File
import java.io.PrintWriter

object Main {
  def main(args: Array[String]): Unit = {
    args match {
      case Array(algo, inFile, outFile, n)
        => run(algo, inFile, outFile, n.toInt)
      case _ => println("Invalid arguments")
    }
  }

  def run(algo: String, inFileOrInput: String, outFile: String, n: Int): Unit = {
    var input = Vector[Int]()
    if (algo == "quicksort") {
      input = (for (line <- Source.fromFile(inFileOrInput).getLines)
        yield line.toInt).toVector
    }

    val impOut = new PrintWriter(new File(outFile + "-imp"))
    val funOut = new PrintWriter(new File(outFile + "-fun"))
    (0 until n).foreach { i =>
      algo match {
        case "primes" =>
          val input = inFileOrInput.toInt

          var start = System.nanoTime
          Primes.imperative(input)
          var end = System.nanoTime
          val impTime = end - start
          impOut.println(i + ", " + impTime)

          start = System.nanoTime
          Primes.functional(input)
          end = System.nanoTime
          val funTime = end - start
          funOut.println(i + ", " + funTime)

        case "fibonacci" =>
          val input = inFileOrInput.toInt

          var start = System.nanoTime
          Fibonacci.imperative(input)
          var end = System.nanoTime
          val impTime = end - start
          impOut.println(i + ", " + impTime)

          start = System.nanoTime
          Fibonacci.functional(input)
          end = System.nanoTime
          val funTime = end - start
  
          funOut.println(i + ", " + funTime)

        case "quicksort" =>
          val arr = new Array[Int](input.length)
          for (i <- 0 until input.length) {
            arr(i) = input(i)
          }

          var start = System.nanoTime
          Quicksort.imperative(arr)
          var end = System.nanoTime
          val impTime = end - start
          impOut.println(i + ", " + impTime)

          val arr1 = new Array[Int](input.length)
          for (i <- 0 until input.length) {
            arr1(i) = input(i)
          }

          start = System.nanoTime
          Quicksort.functional(arr1)
          end = System.nanoTime
          val funTime = end -  start
          funOut.println(i + ", " + funTime)
      }
    }
    impOut.close()
    funOut.close()
  }
}
