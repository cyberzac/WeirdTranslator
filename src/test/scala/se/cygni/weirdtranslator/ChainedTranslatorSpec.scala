package se.cygni.weirdtranslator

import org.specs.Specification
import org.specs.mock.Mockito


class ChainedTranslatorSpec extends Specification with ChainedTranslatorComponentImpl with TranslatorComponent with Mockito {

  override val translator = mock[Translator]

  "createLanguagesPairs" should {

    "return an empty list for sv, list()" in {
      val pairs = createLanguagePairs("sv", List.empty)
      pairs must_== List.empty[LanguagePair]


      """return (("sv", "en"), ("en, "sv")) for sv, list("en")""" in {
        val pairs = createLanguagePairs("sv", List("en"))
        pairs must_== List(LanguagePair("sv", "en"), LanguagePair("en", "sv"))
      }

      """return (("sv", "en"), ("en", de"), ("de, "sv")) for sv, list("en","de")""" in {
        val pairs = createLanguagePairs("sv", List("en", "de"))
        pairs must_== List(LanguagePair("sv", "en"), LanguagePair("en", "de"), LanguagePair("de", "sv"))
      }

    }

  }
}