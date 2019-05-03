package catalysts
package macros

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

import macrocompat.bundle

object ClassInfo {
  def className(that: Any): String = macro ClassInfoMacros.classNameImpl
}

/**
 * Do not use this directly, rather [[catalysts.macros.ClassInfo]]
 */
@bundle
class ClassInfoMacros(val c: blackbox.Context) {
  import c.universe._

  def classNameImpl(that: c.Expr[Any]): c.Expr[String] =
    c.Expr[String](q"$that.getClass.getName")
}

