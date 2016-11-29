package com.mariotti.urlshortener.dao.persistence

import com.mariotti.urlshortener.model.Stats
import java.nio.file.{ Paths, Files }
import scala.io.Source
import java.nio.charset.StandardCharsets
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{ read, write }

object Persistence extends PersistenceWrapper {
  val MAPPING_PREFIX = "MAPPING_"
  val STATS_PREFIX = "STATS_"

  /**
   * NOTE: this is where one could hide some sharding logic so not to overload the inode system
   * and create n levels of folders containing the files. Better yet, this is where you should
   * scrap everything and add a database connector!
   */
  private def getMappingFileName(id: Int): String = {
    MAPPING_PREFIX + String.valueOf(id)
  }

  /**
   * See above note
   */
  private def getStatsFileName(id: Int): String = {
    STATS_PREFIX + String.valueOf(id)
  }

  def get(id: Int): String = {
    val filename = getMappingFileName(id);
    if (Files.exists(Paths.get(filename))) {
      Source.fromFile(filename).getLines.mkString
    } else
      null;
  }
  def put(id: Int, value: String) {
    val filename = getMappingFileName(id);
    if (!Files.exists(Paths.get(filename))) {
      Files.write(Paths.get(filename), value.getBytes(StandardCharsets.UTF_8))
    } else
      throw new Exception("key collision detected when writing file")
  }
  def getStats(id: Int): Stats = {
    val filename = getStatsFileName(id);
    implicit val formats = Serialization.formats(NoTypeHints)
    val ser = Source.fromFile(filename).getLines.mkString
    read[Stats](ser)
  }
  def putStats(id: Int, stats: Stats) {
    if (stats != null) {
      val filename = getStatsFileName(id);
      implicit val formats = Serialization.formats(NoTypeHints)
      val ser = write(stats)
      Files.write(Paths.get(filename), ser.getBytes(StandardCharsets.UTF_8))
    }
  }
}