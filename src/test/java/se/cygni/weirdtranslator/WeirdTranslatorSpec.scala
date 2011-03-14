package se.cygni.weirdtranslator

import org.specs.Specification


class WeirdTranslatorSpec extends Specification {

  "createLanguagesPairs" should {
    val dut = new WeirdTranslator

    "return an empty list for sv, list()" in {
      val pairs = dut.createLanguagePairs("sv", List.empty)
      pairs must_== List.empty[(String, String)]
    }

    """return (("sv", "en"), ("en, "sv")) for sv, list("en")""" in {
      val pairs = dut.createLanguagePairs("sv", List("en"))
      pairs must_== List(("sv", "en"), ("en", "sv"))
    }

    """return (("sv", "en"), ("en", de"), ("de, "sv")) for sv, list("en","de")""" in {
      val pairs = dut.createLanguagePairs("sv", List("en", "de"))
      pairs must_== List(("sv", "en"), ("en", "de"), ("de", "sv"))
    }

  }

}