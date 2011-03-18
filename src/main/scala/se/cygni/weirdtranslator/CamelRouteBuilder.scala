package se.cygni.weirdtranslator

import org.apache.camel.scala.dsl.builder.{RouteBuilder => ScalaRouteBuilder}
import org.apache.camel.Exchange

trait CamelRouteBuilder extends ChainedTranslatorComponent {

  def createRouteBuilder(minaPort: Int, xmppAddress: String): ScalaRouteBuilder = new ScalaRouteBuilder {

    val xmpp = "xmpp://" + xmppAddress
    val mina = "mina:tcp://localhost:%s?textline=true&sync=true&encoding=UTF-8".format(minaPort)

    mina process((exchange:Exchange) => {
      val translated = chainedTranslator.translate(exchange.in.toString)
      exchange.out = translated
    })

    xmpp bean(chainedTranslator) to xmpp

  }

}


