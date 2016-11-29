package com.mariotti.urlshortener.dao

import com.mariotti.urlshortener.dao.cache.Cache
import com.mariotti.urlshortener.utils.ShortUrl
import com.mariotti.urlshortener.dao.persistence.MapperReaderDB
import com.mariotti.urlshortener.model.Stats

object MapperReader {

  def readMapping(short: String): String = {
    val id = ShortUrl.decode(short)
    var url = Cache.get(id);
    if (url != null)
      url
    else {
      // hit the DB, cache might be dirty
      url = MapperReaderDB.get(id)
      if (url != null)
        Cache.reload(id, url)
      url
    }
  }
  def readStats(short: String): Stats = {
    val id = ShortUrl.decode(short)
    MapperReaderDB.getStats(id)
  }
}