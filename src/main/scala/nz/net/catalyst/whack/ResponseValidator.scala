package nz.net.catalyst.whack

trait ResponseValidator {
  def validate(response: String): Boolean
}
