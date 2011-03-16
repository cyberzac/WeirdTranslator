package se.cygni.weirdtranslator

import org.apache.camel.scala.dsl.builder.{RouteBuilder => ScalaRouteBuilder}
import org.apache.camel.{CamelContext, Exchange}

trait CamelRouteBuilder extends Logging {

  def bodyStartsWithStar(body: String): Boolean = {
    val star = """^\*""".r
    body match {
      case star() => true
      case _ => false
    }
  }

  val showIntermediates = (exchange: Exchange) => {
    val body = exchange.getIn.getBody(classOf[String])
    bodyStartsWithStar(body)
  }

  val commonRouteBuilder = new ScalaRouteBuilder {
    "seda:in" ==> {
      as(classOf[String])
      choice {
        when(showIntermediates) split (WeirdTranslator.translateIntermediates _) to ("seda:xmpp")
        otherwise bean (WeirdTranslator)
      }
    } --> "seda:xmpp"

  }

  val xmppRouteBuilderRouteBuilder = (address: String) => new ScalaRouteBuilder {
    val xmpp = "xmpp://" + address

    xmpp --> "seda:in"
    "seda:xmpp" --> xmpp
  }

  def createRouteBuilder(xmppAddress: String): ScalaRouteBuilder = new ScalaRouteBuilder {
    override def addRoutesToCamelContext(context: CamelContext) = {
      commonRouteBuilder.addRoutesToCamelContext(context)
      xmppRouteBuilderRouteBuilder(xmppAddress).addRoutesToCamelContext(context)
    }

  }

}


