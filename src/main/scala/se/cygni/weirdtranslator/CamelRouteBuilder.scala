package se.cygni.weirdtranslator

import org.apache.camel.scala.dsl.builder.{RouteBuilder => ScalaRouteBuilder}
import org.apache.camel.{CamelContext, Exchange}

trait CamelRouteBuilder extends Logging with ChainedTranslatorComponent {

  def commonRouteBuilder = new ScalaRouteBuilder {
    "seda:in" bean(chainedTranslator) to ("seda:out")
  }

  def xmppRouteBuilderRouteBuilder(address: String) = new ScalaRouteBuilder {
    val xmpp = "xmpp://" + address

    xmpp --> "seda:in"
    "seda:out" --> xmpp
  }

  def createRouteBuilder(xmppAddress: String): ScalaRouteBuilder = new ScalaRouteBuilder {
    override def addRoutesToCamelContext(context: CamelContext) = {
      commonRouteBuilder.addRoutesToCamelContext(context)
      xmppRouteBuilderRouteBuilder(xmppAddress).addRoutesToCamelContext(context)
    }
  }

}


