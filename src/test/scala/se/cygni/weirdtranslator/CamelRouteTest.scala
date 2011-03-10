package se.cygni.weirdtranslator

import org.apache.camel.component.mock.MockEndpoint
import org.apache.camel.scala.dsl.builder.{RouteBuilder => ScalaRouteBuilder}
import org.apache.camel.test.CamelTestSupport
import org.testng.annotations.{AfterMethod, BeforeTest, Test}
import org.apache.camel.{RoutesBuilder, EndpointInject, Produce, ProducerTemplate}

class CamelRouteTest extends CamelTestSupport  {

  setUseRouteBuilder(false);

  @EndpointInject(uri = "mock:result")  var resultEndpoint: MockEndpoint = null
  @Produce(uri = "direct:start")  var producerTemplate: ProducerTemplate = null

  @BeforeTest
  def beforeTest: Unit = {
    setUp
    context.addRoutes(createBuilder)   //Workaround since the super.createRouteBuilder returns a RouteBuilderFactory class
    startCamelContext
  }

  @AfterMethod
  def afterTest: Unit = {
     resetMocks
  }

  @Test
  def testSendMatchingMessage: Unit = {
    var expectedBody = <matched/>
    resultEndpoint.expectedBodiesReceived(expectedBody)
    producerTemplate.sendBody(expectedBody)
    resultEndpoint.assertIsSatisfied
  }

  @Test
  def testSendNotMatchingMessage: Unit = {
    resultEndpoint.expectedMessageCount(0)
    producerTemplate.sendBody(<notMatched/>)
    resultEndpoint.assertIsSatisfied
  }

  def createBuilder = new ScalaRouteBuilder  {
    "direct:start" when (_.in == <matched/>)  to   "mock:result"
  }

}