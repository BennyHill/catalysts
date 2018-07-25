package catalysts

object Compiler {
  // using `final val` makes compiler constant-fold any use of these values, dropping dead code automatically
  // $COVERAGE-OFF$
  final val is2_11 = false
  final val is2_12 = false
  final val is2_13 = true
  // $COVERAGE-ON$
}
