package com.mariotti.functions

import scala.collection.mutable.HashMap

object Fibonacci {
  private def fib(n: Int, m: HashMap[Int, Int] = HashMap(0 -> 0, 1 -> 1)): Int = {
    if (n <= 0 || n == 1 || m.contains(n)) {
      m.get(n).get
    } else {
      val value = fib(n - 1) + fib(n - 2)
      m.put(n, value)
      value
    }
  }
  def get(n: Int): Int = fib(n)
}