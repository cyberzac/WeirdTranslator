package se.cygni.weirdtranslator

trait DispatchHttpClientComponent extends HttpClientComponent {

  class DispatchHttpClient extends HttpClient {

    import dispatch.Http
    import Http._

    val http = new Http

    def http(url: String): String = {
      http(url as_str)
    }
  }

}