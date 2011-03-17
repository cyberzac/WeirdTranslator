package se.cygni.weirdtranslator

import org.specs.Specification

class CamelRouteBuilderSpec extends Specification with  SpecEnvironment with  CamelRouteBuilder {

       "showIntermediates" should {

         "return true if the body starts with a *" in {
            bodyStartsWithStar("*hej") must_== true
         }
          "return true if the body starts is  a*" in {
            bodyStartsWithStar("*") must_== true
         }
         "return false if the body does not start with a *" in {
           bodyStartsWithStar("hej") must_== false
         }

         "return false for the empty string" in {
           bodyStartsWithStar("")
         }
       }
}