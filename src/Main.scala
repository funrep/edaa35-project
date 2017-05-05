

import scala.io.Source
import java.io.File
import java.io.PrintWriter
import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer
import scala.annotation.tailrec
 
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
 
     val impOut = new PrintWriter(new File(outFile + "-imp.csv"))
     val funOut = new PrintWriter(new File(outFile + "-fun.csv"))
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

 object Fibonacci {
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

  def functional(input: Int): BigInt = {
    @tailrec
    def iter(n: Int, a: BigInt, b: BigInt): BigInt = n match {
      case 0 => a
      case 1 => b
      case _ => iter(n - 1, b, a + b)
    }
    iter(input, 0, 1)
  }
}

object Quicksort {
  def imperative(xs: Array[Int]) {
    def swap(i: Int, j: Int) {
        val t = xs(i); xs(i) = xs(j); xs(j) = t
    }

    def sort(l: Int, r: Int) {
        val pivot = xs((l + r) / 2)
        var i = l; var j = r
        while (i <= j) {
            while (xs(i) < pivot) i += 1
            while (xs(j) > pivot) j -= 1
            if (i <= j) {
                swap(i, j)
                i += 1
                j -= 1
            }
        }
        if (l < j) sort(l, j)
        if (j < r) sort(i, r)
    }

    sort(0, xs.length - 1)
}
  def functional(xs: Array[Int]): Array[Int] = {
    if (xs.length <= 1) xs
    else {
        val pivot = xs(xs.length / 2)
        Array.concat(
            functional(xs filter (pivot >)), 
            (xs filter (pivot ==)), 
            functional(xs filter (pivot <)))
    }
}
}

object Primes {

  def functional(max: Int): Vector[Int] = {
    @tailrec
    def inner(primes: Vector[Int], current: Int): Vector[Int] = {
      if (current > max) {
        primes
      }
      else if (primes.forall(prime => current % prime != 0)) {
        inner(primes :+ current, current + 1)
      }
      else {
        inner(primes, current + 1)
      }
    }
    inner(Vector.empty, 2);
  }

  def imperative(max: Int): ArrayBuffer[Int] = {
    val primes: ArrayBuffer[Int] = ArrayBuffer.empty
    var i = 2;
    var n = 0;
    while (i <= max) {
      var j = 0;
      var isPrime = true;
      while (j < n && isPrime) {
        if (i % primes(j) == 0) {
          isPrime = false;
        }
        j += 1;
      }
      if (isPrime) {
        primes.append(i);
        n += 1;
      }
      i += 1;
    }
    primes;
  }
}