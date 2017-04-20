package d0x10

import scala.io.Source
import java.io.File
import java.io.PrintWriter
import collection.mutable.ArrayBuffer

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

          val impTime = execTime(() => Primes.imperative(input))
          impOut.println(i + ", " + impTime)

          val funTime = execTime(() => Primes.functional(input))
          funOut.println(i + ", " + funTime)

        case "fibonacci" =>
          val input = inFileOrInput.toInt

          val impTime = execTime(() => Fibonacci.imperative(input))
          impOut.println(i + ", " + impTime)

          val funTime = execTime(() => Fibonacci.functional(input))
          funOut.println(i + ", " + funTime) 

        case "quicksort" =>
          val impTime = execTime(() => Quicksort.imperative(input))
          impOut.println(i + ", " + impTime)

          val funTime = execTime(() => Quicksort.functional(input))
          funOut.println(i + ", " + funTime)
      }
    }
    impOut.close()
    funOut.close()
  }

  def execTime(proc: => Unit): Long = {
    val start = System.nanoTime
    proc
    val end = System.nanoTime
    end - start
  }
}
