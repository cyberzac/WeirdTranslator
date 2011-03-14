package se.cygni.weirdtranslator

import org.apache.camel.impl.DefaultCamelContext


object Main extends XmppRouteBuilder {

  val xmppAddressTemplate = "talk.google.com:5222/%s?serviceName=%s&user=%s&password=%s"

  def main(args: Array[String]) = {
    if (args.length != 3) {
      println("Usage:  toUser fromUser password")
      exit
    }

    val (to, domain) = extractUserAndDomain(args(0))
    val (from, _) = extractUserAndDomain(args(1))
    val password = args(2)
    val xmppAddress = xmppAddressTemplate.format(to, domain, from, password)

    val context = new DefaultCamelContext()
    context.addRoutes(createRouteBuilder(xmppAddress))
    context.start
    while (true) {
      Thread.sleep(10);
    }
  }

  def extractUserAndDomain(arg: String): (String, String) = {
    val defaultDomain = "gmail.com"
    arg.split("@") match {
      case Array(user) => (user + "@" + defaultDomain, defaultDomain)
      case Array(user, domain) => (arg, domain)
    }
  }

}