package se.cygni.weirdtranslator

import org.apache.camel.impl.DefaultCamelContext


object CamelContext extends GoogleTranslateComponent with XmppRouteBuilderFactoryComponent with DispatchHttpClientComponent {

  // Inject  implementations
  val translate = new GoogleTranslate
  val routeBuilderFactory = new XmppRouteBuilderFactory()
  val httpClient = new DispatchHttpClient

  val xmppAdress = "talk.google.com:5222/martin.zachrison@gmail.com?serviceName=gmail.com&user=weirdtranslator@gmail.com&password=drag-ham"

  def main(args: Array[String]) = {
    val context = new DefaultCamelContext()
    context.addRoutes(routeBuilderFactory.createRouteBuilder(xmppAdress))
    context.start
    while (true) {
      Thread.sleep(10);
    }
  }

}