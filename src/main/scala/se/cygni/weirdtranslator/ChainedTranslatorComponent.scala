package se.cygni.weirdtranslator



trait ChainedTranslatorComponent {

  val chainedTranslator: ChainedTranslator
  trait ChainedTranslator {
    def translate(text: String): String
  }
}