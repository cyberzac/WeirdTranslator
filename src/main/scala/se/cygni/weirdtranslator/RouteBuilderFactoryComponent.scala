package se.cygni.weirdtranslator

import org.apache.camel.scala.dsl.builder.RouteBuilder

trait RouteBuilderFactoryComponent  {

  val routeBuilderFactory: RouteBuilderFactory

  trait RouteBuilderFactory  {
        def createRouteBuilder(address:String): RouteBuilder
  }
}

