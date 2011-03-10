package se.cygni.weirdtranslator

import org.apache.camel.Exchange
import org.slf4j.LoggerFactory


trait XmppRouteBuilderFactoryComponent extends RouteBuilderFactoryComponent  {
  this: TranslateComponent =>

  class XmppRouteBuilderFactory extends RouteBuilderFactory with Logging{

    import org.apache.camel.scala.dsl.builder.RouteBuilder

    def createRouteBuilder(address: String) = new RouteBuilder {
      val xmpp = "xmpp://" + address
      val translator: (Exchange) => Unit = (exchange: Exchange) => {
      try {
        val text = exchange.in.toString
        val from = translate.identifyLang(text)
        val languages = List((from, "it"), ("it", "sv"), ("sv", "de"), ("de", "en"), ("en", from))
        exchange.in = languages.foldLeft(text)(translate.translateText(_, _))
      } catch {
        case np: NullPointerException => //Ignore, spurious XMPP empty messages
        case e => error("Failed translating xmpp message", e)
      }
    }

      xmpp process (translator) to xmpp

    }
  }

}
