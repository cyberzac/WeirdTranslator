package se.cygni.weirdtranslator

import org.apache.camel.impl.DefaultCamelContext


object Main extends XmppRouteBuilder {

  val xmppAddressTemplate = "talk.google.com:5222/%s?serviceName=%s&user=weirdtranslator@gmail.com&password=drag-ham"

  def main(args: Array[String]) = {
    if (args.length == 0) {
      println("Must give gtalk user to talk to")
    }

    val (user, domain) = extractUserDomain(args(0))
    val xmppAddress = xmppAddressTemplate.format(user, domain)

    val context = new DefaultCamelContext()
    context.addRoutes(createRouteBuilder(xmppAddress))
    context.start
    while (true) {
      Thread.sleep(10);
    }
  }

  def extractUserDomain(arg: String): (String, String) = {
    val defaultDomain = "gmail.com"
    arg.split("@") match {
      case Array(user) =>  (user + "@" + defaultDomain, defaultDomain)
      case Array(user, domain) => (arg, domain)
    }
  }


}