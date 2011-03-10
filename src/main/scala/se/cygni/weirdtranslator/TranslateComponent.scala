package se.cygni.weirdtranslator

trait TranslateComponent {

  val translate: Translate

  trait Translate {

    def translateText(text: String, fromTo: Pair[String, String]): String

    def identifyLang(text: String): String
  }

}