//Created By Ilan Godik
package nightra.mashovNotificator.network

import nightra.mashovNotificator.xml.{XmlPrinter, XML, Tag}
import spray.http.{MediaTypes, HttpEntity, HttpRequest}
import spray.client.pipelining._
import spray.http.HttpHeaders.RawHeader
import argonaut.DecodeJson

/**
 * Must have an appropriate reader
 **/
trait Response

trait Request[Resp <: Response] {
  def toXML: Tag
  def name: String
}

trait ReaderCompanion[T] {
  implicit def reader: DecodeJson[T]
}

trait ResponseCompanion[T <: Response] extends ReaderCompanion[T] {
  implicit def reader: DecodeJson[T]
}

object Request {
  def addNS(xml: Tag): XML = xml.copy(namespace = "http://tempuri.org/")

  def wrapInSoap(body: String): String =
    s"""<?xml version="1.0" encoding="utf-8"?>
        |<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
        | <soap:Body>
        | $body
        | </soap:Body>
        |</soap:Envelope>""".stripMargin

  def buildSoapRequest(body: String, name: String): HttpRequest =
    Post("http://pda.mashov.info/mashovcellolarstudents/json.asmx",
      HttpEntity(MediaTypes.`text/xml`, body))
      .withHeaders(RawHeader("SOAPAction", s"http://tempuri.org/$name"))

  def prepareRequest(req: Request[_]): HttpRequest = {
    val xml = req.toXML
    val withNS = addNS(xml)

    val stringBody = XmlPrinter.showXMLCompact.shows(withNS)
    val soapXML = wrapInSoap(stringBody)

    val name = xml.label
    val soapRequest = buildSoapRequest(soapXML, name)
    soapRequest
  }
}
