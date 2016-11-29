package com.mariotti.urlshortener.dao.persistence

import com.mariotti.urlshortener.model.Stats

object MapperWriterDB {
  def put(id:Int, value:String){
    Persistence.put(id, value)
  }
  def putStats(id:Int, stats:Stats){
    Persistence.putStats(id, stats)
  }
}