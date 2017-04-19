package d0x10

abstract class Algorithm[I,O]() {
  def imperative(input: I): O
  def functional(input: I): O
}