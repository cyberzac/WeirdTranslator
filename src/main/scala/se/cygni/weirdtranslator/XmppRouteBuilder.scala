package se.cygni.weirdtranslator

import org.apache.camel.scala.dsl.builder.RouteBuilder

trait XmppRouteBuilder extends Logging {

  def createRouteBuilder(address: String) = new RouteBuilder {
    val xmpp = "xmpp://" + address

    xmpp --> "bean:se.cygni.weirdtranslator.WeirdTranslator " --> xmpp

  }

}


