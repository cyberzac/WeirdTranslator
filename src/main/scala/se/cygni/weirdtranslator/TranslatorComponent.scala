package se.cygni.weirdtranslator



trait TranslatorComponent  {

  val translator : Translator

  case class LanguagePair(from:String, to:String)

  trait Translator {
     def translateText(text: String, fromTo: LanguagePair): String
     def identifyLang(text: String): String
  }


}