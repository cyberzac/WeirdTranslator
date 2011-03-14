package se.cygni.weirdtranslator

import org.apache.camel.impl.DefaultCamelContext


object Main extends XmppRouteBuilder{

  val xmppAdress = "talk.google.com:5222/zac@cyberzac.se?serviceName=gmail.com&user=weirdtranslator@gmail.com&password=drag-ham"

  def main(args: Array[String]) = {
    val context = new DefaultCamelContext()
    context.addRoutes(createRouteBuilder(xmppAdress))
    context.start
    while (true) {
      Thread.sleep(10);
    }
  }

}