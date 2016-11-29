package com.mariotti.urlshortener.dao.persistence

import com.mariotti.urlshortener.model.Stats

trait PersistenceWrapper {
  def get(id: Int): String
  def put(id: Int, value: String)
  def getStats(id: Int): Stats
  def putStats(id: Int, stats: Stats)
}