package catalysts
package testkit
package syntax
package junit

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import macrocompat.bundle

import scala.annotation.StaticAnnotation

import scala.annotation.compileTimeOnly

@compileTimeOnly("enable macro paradise or add the -Ymacro-annotations flag to expand macro annotations")
class Test extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro TestMacro.impl
}

@bundle
class TestMacro(val c: whitebox.Context) {

  import c.universe._

  def impl(annottees: c.Expr[Any]*): c.Expr[Any] = {

    val inputs = annottees.map(_.tree). toList
    val (annottee, expandees) = inputs match {
      case (param: ValDef) :: (rest @ (_ :: _)) => (param, rest)
      case (param: TypeDef) :: (rest @ (_ :: _)) => (param, rest)
      case _ => (EmptyTree, inputs)
    }

    val testName = expandees(0).toString().replace("def ", "").split('(').head.split(":").head
    val testTree =  annottees.map(_.tree)

    val testMethod = TermName(testName)
    val exp =
      q"""
        test($testName) {
          $testMethod
         }
         ..$testTree
      """

    c.Expr[Any](exp)
  }
}

object Assert {
  def assertArrayEquals = 0
}
