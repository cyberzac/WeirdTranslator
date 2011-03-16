package se.cygni.weirdtranslator

import org.apache.camel.scala.dsl.builder.RouteBuilder
import org.apache.camel.Exchange

trait XmppRouteBuilder extends Logging {

  def bodyStartsWithStar(body: String): Boolean = {
    val star = """^\*""".r
    body match {
      case star() => true
      case _ => false
    }
  }

  def createRouteBuilder(address: String) = new RouteBuilder {

    val xmpp = "xmpp://" + address
    val showIntermediates = (exchange: Exchange) => {
      val body = exchange.getIn.getBody(classOf[String])
      bodyStartsWithStar(body)
    }

    xmpp --> "seda:in"

    "seda:out" --> xmpp

    "seda:in" ==> {
      as(classOf[String])
      choice {
        when(showIntermediates) split (WeirdTranslator.translateIntermediates _)  to ( "seda:out")
        otherwise bean (WeirdTranslator)
      }
    } --> "seda:out"


    // xmpp bean(new se.cygni.se.cygni.weirdtranslator.WeirdTranslator) to xmpp
    //  xmpp split( se.cygni.se.cygni.weirdtranslator.WeirdTranslator.translateWeird _ )  to xmpp
    //xmpp ==> {
    // as(classOf[String])
    // split(WeirdTranslator.translateWeird )  to xmpp
    //  split(WeirdTranslator.translateWeird _)  to xmpp
    // }
  }

}


