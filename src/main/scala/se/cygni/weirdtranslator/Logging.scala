package se.cygni.weirdtranslator

import org.slf4j.LoggerFactory

trait Logging {

  protected val logger = LoggerFactory.getLogger(getClass)

  def trace(message: String, values: Any*) = logger.trace(message, values.map(_.asInstanceOf[Object]).toArray)

  def debug(message: String, values: Any*) = logger.debug(message, values.map(_.asInstanceOf[Object]).toArray)

  def info(message: String, values: Any*) = logger.info(message, values.map(_.asInstanceOf[Object]).toArray)

  def warn(message: String, values: Any*) = logger.warn(message, values.map(_.asInstanceOf[Object]).toArray)

  def error(message: String, values: Any*) = logger.error(message, values.map(_.asInstanceOf[Object]).toArray)

}