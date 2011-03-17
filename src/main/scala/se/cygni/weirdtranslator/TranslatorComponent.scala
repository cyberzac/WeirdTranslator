package se.cygni.weirdtranslator



trait TranslatorComponent  {

  val translator : Translator

  trait Translator {
     def translateText(text: String, fromTo: Pair[String, String]): String
     def identifyLang(text: String): String
  }


}