package com.mariotti.urlshortener.dao.cache

import scala.collection.mutable.HashMap
import scala.util.Random

/**
 * This singleton would need to be re-written to actually talk to a distributed and scalable cache like Redis
 */
object Cache extends CacheWrapper {
  private var cache: HashMap[Int, String] = new HashMap()
  private val random: Random = new Random()

  def get(id: Int): String = {
    if (cache.contains(id))
      cache.get(id).get
    else
      null
  }
  def put(value: String): Int = {
    var id: Int = random.nextInt()
    while (cache.contains(id) || id<0)
      id = random.nextInt()
    cache.put(id, value)
    id
  }

  def reload(id: Int, value: String) {
    cache.put(id, value)
  }

}