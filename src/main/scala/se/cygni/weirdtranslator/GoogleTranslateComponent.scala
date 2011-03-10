package se.cygni.weirdtranslator

import net.liftweb.util.Helpers
import dispatch.Http


trait GoogleTranslateComponent extends TranslateComponent with Logging {
  this : HttpClientComponent  =>

  import net.liftweb.json.JsonParser._

  class GoogleTranslate extends Translate {
    val baseUrl = "http://ajax.googleapis.com/ajax/services/language/"
    val detectUrl = baseUrl + "detect?v=1.0&q="
    val translateUrl = baseUrl + "translate?v=1.0&q="

    implicit val formats = net.liftweb.json.DefaultFormats

    def translateText(text: String, fromTo: Pair[String, String]): String = {
      val (from, to) = fromTo
      val url = translateUrl + Helpers.urlEncode(text) + "&langpair=" + from + "%7C" + to
      val translated = extractJsonField(url, "translatedText").getOrElse(return "I'm sorry?")
      debug("Translating from {} -> {} =>  {}", from, to, translated)
      // Note that Google inserts a space after a # and a @
      translated.replace("# ", "#").replace("@ ", "@")
    }

    def identifyLang(text: String): String = {
      val lang = extractJsonField(detectUrl + Helpers.urlEncode(text), "language").getOrElse(return "Sounds like greek to me")
      debug("""Identified language {}, from "{}"""", lang, text)
      lang
    }

    private def extractJsonField(url: String, field: String): Option[String] = {
      val json = httpClient.http(url)
      val parsed = parse(json)
      val status = (parsed \\ "responseStatus").extract[String]
      if (status == "200") {
        Some((parsed \\ field).extract[String])
      } else {
        None
      }
    }
  }

}

