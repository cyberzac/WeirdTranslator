package se.cygni.weirdtranslator

import org.slf4j.LoggerFactory

trait Logging {

  private val cygniLogger = LoggerFactory.getLogger(getClass)

  def trace(message: String, values: Any*) = cygniLogger.trace(message, values.map(_.asInstanceOf[Object]).toArray)

  def debug(message: String, values: Any*) = cygniLogger.debug(message, values.map(_.asInstanceOf[Object]).toArray)

  def info(message: String, values: Any*) = cygniLogger.info(message, values.map(_.asInstanceOf[Object]).toArray)

  def warn(message: String, values: Any*) = cygniLogger.warn(message, values.map(_.asInstanceOf[Object]).toArray)

  def error(message: String, values: Any*) = cygniLogger.error(message, values.map(_.asInstanceOf[Object]).toArray)

}