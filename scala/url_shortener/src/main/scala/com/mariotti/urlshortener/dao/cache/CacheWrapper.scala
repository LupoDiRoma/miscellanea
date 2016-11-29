package com.mariotti.urlshortener.dao.cache

trait CacheWrapper {
  def get(key: Int): String
  def put(value: String): Int
  def reload(id:Int, value:String)
}