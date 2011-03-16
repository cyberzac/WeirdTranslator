package se.cygni.weirdtranslator

import org.apache.camel.{Handler, Body}

object WeirdTranslator extends GoogleTranslate with Logging {

  val languages = List("it", "pl", "de", "fr", "es")



  @Handler
  def translateOneStep(@Body text:String): String = {
    if (text == null || text.isEmpty) return null
    val translatePairs = createLanguagePairs(text, languages)
    translatePairs.foldLeft(text)((t, p) => translateText(t, p))
  }

  def translateIntermediates(text: String): Array[String] = {

    def translate(text: String, l: List[Pair[String, String]]): List[String] = l match {
      case Nil => Nil
      case head :: tail => {
        val translation = translateText(text, head)
        "%s (%s)".format(translation, head._2) :: translate(translation, tail)
      }
    }
    if (text == null || text.isEmpty) return Array()
    val translatePairs = createLanguagePairs(text, languages)
    translate(text, translatePairs).toArray
  }

  def createLanguagePairs(text: String, languages: List[String]): List[(String, String)] = {
    def pairs(first: String, tail: List[String]): List[(String, String)] = tail match {
      case Nil => Nil
      case head :: tail => (first, head) :: pairs(head, tail)
    }

    val first = identifyLang(text)
    languages match {
      case Nil => List.empty[(String, String)]
      case list => pairs(first, list :+ first)
    }

  }

}