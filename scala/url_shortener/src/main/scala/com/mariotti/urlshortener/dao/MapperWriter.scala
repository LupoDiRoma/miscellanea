package com.mariotti.urlshortener.dao

import com.mariotti.urlshortener.dao.cache.Cache
import com.mariotti.urlshortener.utils.ShortUrl
import com.mariotti.urlshortener.dao.persistence.MapperWriterDB
import com.mariotti.urlshortener.dao.persistence.MapperReaderDB
import com.mariotti.urlshortener.model.Stats
import java.io.FileNotFoundException

object MapperWriter {
  def put(url: String): String = {
    val id: Int = Cache.put(url)
    // write to the DB
    MapperWriterDB.put(id, url)
    ShortUrl.encode(id)
  }
  def bump(short: String) {
    var stats: Stats = null
    val id = ShortUrl.decode(short)
    try {
      stats = MapperReader.readStats(short)
      if (stats != null) {
        stats = stats.copy(stats.clicks + 1)
        MapperWriterDB.putStats(id, stats)
      }
    } catch {
      case t: Throwable =>
        // the file does not exists, create it 
        if (t.isInstanceOf[FileNotFoundException]) {
          stats = new Stats(1)
          MapperWriterDB.putStats(id, stats)
        }
        else {
          // TODO log that something happened
          t.printStackTrace()
        }
    }
  }
}