package se.cygni.weirdtranslator



trait HttpClientComponent  {

  val httpClient: HttpClient

  trait HttpClient {
    def http(url:String):String
  }
}