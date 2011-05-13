package nz.net.catalyst.whack

import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.`type`.TypeReference // type is reserved word, need to put backticks
import java.io._


object JsonEnDecoder {

  val mapper = new ObjectMapper()

  def encode(obj: AnyRef): String = {
    mapper.writeValueAsString(obj)
  }

  def decode[T](inputStream: InputStream): T = {
    // still has problems, T is not passed properly
    mapper.readValue(inputStream, new TypeReference[T]() {})

  }

  def decode[T: Manifest](string: String): T = {
//    mapper.readValue(string, new TypeReference[T]() {})
    mapper.readValue(string, new TypeReference[java.util.Map[String, String]]() {})
//    var myMap = new T();
//    return myMap;
  }

}
