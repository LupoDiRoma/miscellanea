package com.mariotti.urlshortener

/**
 * this could either be extended to read/encapsulate a properties reader or it could be removed and
 * each module would port to read the properties independently
 */
object Configuration {
  val HOST: String = "localhost"
  val PROTOCOL:String = "http://"
  val PORT: Int = 8080
}