package se.cygni.weirdtranslator

import org.scalatest.testng.TestNGSuite
import org.scalatest.mock.MockitoSugar

trait TestEnvironment extends TestNGSuite
with MockitoSugar
with GoogleTranslateComponent
with XmppRouteBuilderFactoryComponent
with HttpClientComponent {

  /*
   * Mock all instances
   */
  val translate = mock[Translate]
  val routeBuilderFactory = mock[RouteBuilderFactory]
  val httpClient = mock[HttpClient]

}
