package se.cygni.weirdtranslator

trait ChainedTranslatorComponentImpl extends ChainedTranslatorComponent {
  this: TranslatorComponent =>

  val chainedTranslator = new ChainedTranslatorImpl

  class ChainedTranslatorImpl extends ChainedTranslator{
    def translate(text: String): String = {
      if (text == null || text.isEmpty) return null
      val first = translator.identifyLang(text)
      val translatePairs = createLanguagePairs(first, languages)
      translatePairs.foldLeft(text)((t, p) => translator.translateText(t, p))
    }
  }

  val languages = List("it", "pl", "de", "fr", "es")

  def createLanguagePairs(first: String, languages: List[String]): List[LanguagePair] = {

    def pairs(first: String, tail: List[String]): List[LanguagePair] = tail match {
      case Nil => Nil
      case head :: tail => LanguagePair(first, head) :: pairs(head, tail)
    }
    languages match {
      case Nil => List.empty[LanguagePair]
      case list => pairs(first, list :+ first)
    }

  }
}