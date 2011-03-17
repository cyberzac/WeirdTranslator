package se.cygni.weirdtranslator

import org.apache.camel.component.mock.MockEndpoint
import org.apache.camel.scala.dsl.builder.{RouteBuilder => ScalaRouteBuilder}
import org.apache.camel.test.junit4.CamelTestSupport
import org.apache.camel.{EndpointInject, Produce, ProducerTemplate}
import org.junit.{Test, After, Before}
import org.scalatest.junit.JUnitSuite
class CamelRouteTest extends CamelTestSupport with CamelRouteBuilder with JUnitSuite with TestEnvironment {


  setUseRouteBuilder(false)

  @EndpointInject(uri = "mock:result") var resultEndpoint: MockEndpoint = null
  @Produce(uri = "direct:start") var producerTemplate: ProducerTemplate = null

  @Before
  def beforeTest: Unit = {
    setUp
    context.addRoutes(createBuilder) //Workaround since the super.createRouteBuilder returns a RouteBuilderFactory class
    context.addRoutes(commonRouteBuilder)
    startCamelContext
  }

  @After
  def afterTest: Unit = {
    stopCamelContext
    resetMocks
  }

  @Test
  def chainedRoute: Unit = {
    val message = "Welcome"
    val expected = "Hej"
    mockitoMocker.when(chainedTranslator.translate(message)).thenReturn(expected)
    resultEndpoint.expectedBodiesReceived(expected)
    producerTemplate.sendBody(message)
    resultEndpoint.assertIsSatisfied
  }


  @Test
  def intermediateChainedRoute {
    val message = "*Welcome"
    val expected = Array("Hej", "Welcome")
    mockitoMocker.when(intermediateChainedTranslator.translate(message)).thenReturn(expected)
    resultEndpoint.expectedBodiesReceived(expected(0), expected(1))
    producerTemplate.sendBody(message)
    resultEndpoint.assertIsSatisfied
  }

  def createBuilder = new ScalaRouteBuilder {
    "direct:start" --> "seda:in"
     "seda:out" --> "mock:result"
  }

}