package se.cygni.weirdtranslator

import net.liftweb.util.Helpers
import dispatch.Http
import Http._

trait GoogleTranslate extends Logging {

  import net.liftweb.json.JsonParser._

  val baseUrl = "http://ajax.googleapis.com/ajax/services/language/"
  val detectUrl = baseUrl + "detect?v=1.0&q="
  val translateUrl = baseUrl + "translate?v=1.0&q="
  val http = new Http

  implicit val formats = net.liftweb.json.DefaultFormats

  def translateText(text: String, fromTo: Pair[String, String]): String = {
    if (text.isEmpty) return ""
    val (from, to) = fromTo
    val url = translateUrl + Helpers.urlEncode(text) + "&langpair=" + from + "%7C" + to
    val translated = extractJsonField(url, "translatedText").getOrElse(return "")
    debug("Translating from {} -> {} =>  {}", from, to, translated)
    // Note that Google inserts a space after a # and a @
    translated.replace("# ", "#").replace("@ ", "@")
  }

  def identifyLang(text: String): Option[String] = {
    val lang = extractJsonField(detectUrl + Helpers.urlEncode(text), "language").getOrElse(return None)
    debug("""Identified language {}, from "{}"""", lang, text)
    Some(lang)
  }

  private def extractJsonField(url: String, field: String): Option[String] = {
    val json = http(url as_str)
    val parsed = parse(json)
    val status = (parsed \\ "responseStatus").extract[String]
    if (status == "200") {
      Some((parsed \\ field).extract[String])
    } else {
      None
    }
  }

}

