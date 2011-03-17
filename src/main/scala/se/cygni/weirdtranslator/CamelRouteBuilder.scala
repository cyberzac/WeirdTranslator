package se.cygni.weirdtranslator

import org.apache.camel.scala.dsl.builder.{RouteBuilder => ScalaRouteBuilder}
import org.apache.camel.{CamelContext, Exchange}

trait CamelRouteBuilder extends Logging with ChainedTranslatorComponent {

  def bodyStartsWithStar(body: String): Boolean = {
    val star = "^\\*.*".r
    body match {
      case star() => true
      case _ => false
    }
  }

  val showIntermediates = (exchange: Exchange) => {
    val body = exchange.getIn.getBody(classOf[String])
    bodyStartsWithStar(body)
  }

  def commonRouteBuilder = new ScalaRouteBuilder {
    "seda:in" ==> {
      choice {
        when(showIntermediates) log ("Splitting") split (intermediateChainedTranslator.translate _) to ("seda:out")
        otherwise log ("bean") bean (chainedTranslator) to ("seda:out")
      }
    }
    /*
      choice {
        when(showIntermediates)  log("Splitting") split (intermediateChainedTranslator.translate) to("seda:out")
        otherwise log("bean")  bean(chainedTranslator)  to("seda:out")
      }
      */
  }

  val xmppRouteBuilderRouteBuilder = (address: String) => new ScalaRouteBuilder {
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


