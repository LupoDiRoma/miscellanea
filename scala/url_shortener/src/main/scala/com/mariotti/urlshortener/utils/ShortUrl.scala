package com.mariotti.urlshortener.utils

/**
 * Attribution: ported to Scala from the following
 * 
 * ShortURL (https://github.com/delight-im/ShortURL)
 * Copyright (c) delight.im (https://www.delight.im/)
 * Licensed under the MIT License (https://opensource.org/licenses/MIT)
 */

/**
 * ShortURL: Bijective conversion between natural numbers (IDs) and short strings
 *
 * ShortURL.encode() takes an ID and turns it into a short string
 * ShortURL.decode() takes a short string and turns it into an ID
 *
 * Features:
 * + large alphabet (51 chars) and thus very short resulting strings
 * + proof against offensive words (removed 'a', 'e', 'i', 'o' and 'u')
 * + unambiguous (removed 'I', 'l', '1', 'O' and '0')
 *
 * Example output:
 * 123456789 <=> pgK8p
 */

object ShortUrl {

  val ALPHABET: String = "23456789bcdfghjkmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ-_"
  val BASE: Int = ALPHABET.length();

  def encode(num: Int): String = {
    var str: StringBuilder = new StringBuilder()
    var n = num
    while (n > 0) {
      str.insert(0, ALPHABET.charAt(n % BASE))
      n = n / BASE
    }
    return str.toString();
  }

  def decode(str: String): Int = {
    var num: Int = 0
    var i: Int = 0
    for (i <- 0 to str.length()-1) {
      num = num * BASE + ALPHABET.indexOf(str.charAt(i))
    }
    return num
  }
}
