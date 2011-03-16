package se.cygni.weirdtranslator

import org.specs.Specification

class XmppRouteBuilderSpec extends Specification with XmppRouteBuilder {

       "showIntermediates" should {

         "return true if the body starts with a *" in {
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