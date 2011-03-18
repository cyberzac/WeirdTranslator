package se.cygni.weirdtranslator

import org.apache.camel.Exchange
import org.apache.camel.scala.dsl.builder.RouteBuilder

trait CamelRouteBuilder extends ChainedTranslatorComponent {

  def createRouteBuilder(minaPort: Int, xmppAddress: String): RouteBuilder = new RouteBuilder {

    val xmpp = "xmpp://" + xmppAddress
    val mina = "mina:tcp://localhost:%s?textline=true&sync=true&encoding=UTF-8".format(minaPort)

    xmpp bean (chainedTranslator) to xmpp

    mina process ((exchange: Exchange) => {
      val translated = chainedTranslator.translate(exchange.in.toString)
      exchange.out = translated
    })

  }

}


