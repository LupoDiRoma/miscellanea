package com.mariotti.urlshortener.dao.persistence

import com.mariotti.urlshortener.model.Stats

object MapperReaderDB {
  def get(id: Int): String = {
    Persistence.get(id)
  }
  def getStats(id: Int): Stats = {
    Persistence.getStats(id)
  }
}