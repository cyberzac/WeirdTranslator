package se.cygni.weirdtranslator

import org.apache.camel.{Handler, Body}

trait ChainedTranslatorComponentImpl extends ChainedTranslatorComponent {
  this: TranslatorComponent =>

  val chainedTranslator = new ChainedTranslatorImpl

  class ChainedTranslatorImpl extends ChainedTranslator with LanguagePairs {
    @Handler
    def translate(@Body text: String): String = {
      if (text == null || text.isEmpty) return null
      val first = translator.identifyLang(text)
      val translatePairs = createLanguagePairs(first, languages)
      translatePairs.foldLeft(text)((t, p) => translator.translateText(t, p))
    }
  }

}

trait LanguagePairs {
  val languages = List("it", "pl", "de", "fr", "es")

  def createLanguagePairs(first: String, languages: List[String]): List[(String, String)] = {

    def pairs(first: String, tail: List[String]): List[(String, String)] = tail match {
      case Nil => Nil
      case head :: tail => (first, head) :: pairs(head, tail)
    }
    languages match {
      case Nil => List.empty[(String, String)]
      case list => pairs(first, list :+ first)
    }

  }
}