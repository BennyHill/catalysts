package catalysts
package macros

import scala.language.experimental.macros
import scala.reflect.macros.blackbox
import macrocompat.bundle

/**
 * TypeTag is runtime reflection, so is not/will not be in scala.js, hence the macro implementation.
 */
trait TypeTagM[T] {
  def name: String
  override def toString: String = name
}

object TypeTagM {
  implicit def apply[T]: TypeTagM[T] = macro TypeTagMacros.applyImpl[T]
  implicit def toString[T](t:TypeTagM[T]): String = t.toString
}

@bundle
class TypeTagMacros(val c: blackbox.Context) {
  import c.universe._

  def applyImpl[T](implicit tTag: WeakTypeTag[T]): c.Expr[TypeTagM[T]] = {
    val tTpe = weakTypeOf[T]
    val tt =
     q"""
        new TypeTagM[$tTpe] {
          def name = ${tTpe.toString}
        }
      """
    c.Expr[TypeTagM[T]](tt)
  }
}
