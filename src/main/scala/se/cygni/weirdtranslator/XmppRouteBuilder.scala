package se.cygni.weirdtranslator

import org.apache.camel.Exchange
import org.apache.camel.scala.dsl.builder.RouteBuilder

trait XmppRouteBuilder extends GoogleTranslate with Logging {

  def createRouteBuilder(address: String) = new RouteBuilder {
    val xmpp = "xmpp://" + address

    def translator(exchange: Exchange): Unit = {
      try {
        val text = exchange.in.toString
        val someLang = identifyLang(text)
        exchange.in = if (someLang.isEmpty) {
          "That sounds like greek to me!"
        } else {
          val lang = someLang.get
          val languages = List((lang, "it"), ("it", "pl"), ("pl", "de"), ("de", "fr"), ("fr", "en"), ("en", lang))
          val translated = languages.foldLeft(text)(translateText(_, _))
          debug("""Translated "%s" -> "%s" """.format(text, translated))
          translated
        }
      } catch {
        case np: NullPointerException => //Ignore, spurious XMPP empty messages
        case e => error("Failed translating xmpp message", e)
      }
    }

    xmpp process (translator _) to xmpp

  }

}


