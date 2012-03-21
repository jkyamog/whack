package nz.net.catalyst.whack

trait ResponseValidator {
  def validate(responseCode: Int, response: String): Boolean
}
