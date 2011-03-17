package se.cygni.weirdtranslator



trait ChainedTranslatorComponent {

  val chainedTranslator: ChainedTranslator
  val intermediateChainedTranslator: IntermediateChainedTranslator

  trait ChainedTranslator {
    def translate(text: String): String
  }

  trait IntermediateChainedTranslator {
    def translate(text: String): Array[String]
  }
}