package se.cygni.weirdtranslator

import net.liftweb.util.Helpers
import dispatch.Http
import Http._

trait GoogleTranslatorComponent extends TranslatorComponent {

  val translator = new GoogleTranslator

  class GoogleTranslator extends Translator with Logging {

    import net.liftweb.json.JsonParser._

    val apiKey = "AIzaSyBcy_kbe8kZg2OTJreo-v59tIwyIdrz0zI"
    val baseUrl = "http://ajax.googleapis.com/ajax/services/language/%s?%s&v=1.0&q="
    val detectUrl = baseUrl.format("detect", apiKey)
    val translateUrl = baseUrl.format("translate", apiKey) + "%s&langpair=%s%%7C%s"
    val http = new Http

    implicit val formats = net.liftweb.json.DefaultFormats

    def translateText(text: String, fromTo: Pair[String, String]): String = {
      if (text.isEmpty) return ""
      val (from, to) = fromTo
      val url = translateUrl.format(Helpers.urlEncode(text), from, to)
      val translated = extractJsonField(url, "translatedText").getOrElse(return "")
      debug("Translated from {} -> {} =>  {}", from, to, translated)
      // Note that Google inserts a space after a # and a @
      translated.replace("# ", "#").replace("@ ", "@")
    }

    def identifyLang(text: String): String = {
      val lang = extractJsonField(detectUrl + Helpers.urlEncode(text), "language").getOrElse("en")
      debug("""Identified language {}, from "{}"""", lang, text)
      lang
    }

    private def extractJsonField(url: String, field: String): Option[String] = {
      val json = http(url as_str)
      val parsed = parse(json)
      val status = (parsed \\ "responseStatus").extract[String]
      if (status == "200") {
        Some((parsed \\ field).extract[String])
      } else {
        warn("Failed {}: {}, url {}", status, (parsed \\ "responseDetails").extract[String], url)
        None
      }
    }

  }


}