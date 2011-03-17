package se.cygni.weirdtranslator

import org.scalatest.mock.MockitoSugar
import org.mockito.MockitoMocker
import org.specs.Specification
import org.specs.mock.Mockito


trait TestEnvironment extends ChainedTranslatorComponent with TranslatorComponent  with MockitoSugar {


  val chainedTranslator =  mock[ChainedTranslator]
  val intermediateChainedTranslator  =  mock[IntermediateChainedTranslator]
  val translator = mock[Translator]
  val mockitoMocker = new MockitoMocker
}

trait SpecEnvironment extends ChainedTranslatorComponent with TranslatorComponent with Mockito{
  val chainedTranslator = mock[ChainedTranslator]
  val intermediateChainedTranslator  = mock[IntermediateChainedTranslator]
  val translator = mock[Translator]
}