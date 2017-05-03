package d0x10

import scala.collection.mutable.ArrayBuffer
import scala.annotation.tailrec

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
