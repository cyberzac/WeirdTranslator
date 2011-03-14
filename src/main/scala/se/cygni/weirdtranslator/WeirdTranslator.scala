package se.cygni.weirdtranslator

import org.apache.camel.{Handler, Body}
class WeirdTranslator extends GoogleTranslate with Logging {

  val languages = List("it", "pl", "de", "fr", "es")

  @Handler
  def translateWeird(@Body text: String): String = {
    val lang = identifyLang(text)
    val translatePairs = createLanguagePairs(lang, languages)
    val translated = translatePairs.foldLeft(text)(translateText(_, _))
    debug("""Translated "%s" -> "%s" """.format(text, translated))
    translated
  }

  def createLanguagePairs(first: String, languages: List[String]): List[(String, String)] = {

   def pair2 (first:String, tail:List[String]): List[(String, String)] =  tail match {
     case Nil => Nil
     case head :: tail =>  (first, head) :: pair2(head, tail)
   }

  languages  match {
     case Nil => List.empty[(String, String)]
     case list => pair2(first, list :+ first)
   }

  }

}