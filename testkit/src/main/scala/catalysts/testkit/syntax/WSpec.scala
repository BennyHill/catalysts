package catalysts
package testkit
package syntax

trait WSpec { self: TestSpec =>

  implicit class StringOps(s: String) {

    def should[A](a: => Any) = nest(s)(a)

    def when[A](a: => Any) = nest(s)(a)

    def would[A](a: => Any) = nest(s"when $s")(a)

    def in[A](a: => A) = block(s"should $s")(a)
  }
}

trait WSpecMatchers { self: TestKit =>

  implicit class AnyOps[A](actual: => A) {

    def must_==(expected: => A): AssertResult =
      macro WSpecMatchersMacros.mustEqEq[A]

    def mustThrowA[T <: Throwable]: ExceptionResult =
      macro WSpecMatchersMacros.mustThrowA[A, T]
  }
}

import scala.reflect.macros.whitebox
import macrocompat.bundle

@bundle
class WSpecMatchersMacros(val c: whitebox.Context)  {
  import c.universe._

  def unpack  = {
    import c.universe._
    c.prefix.tree match {
      case Apply(TypeApply(_, _), List(lhs)) => lhs
      case t => c.abort(c.enclosingPosition,
        "Cannot extract subject of operator (tree = %s)" format t)
    }
  }

  def mustEqEq[A]( expected: Tree): Tree = {
    val lhs = unpack
    q"assertEqEqImpl($lhs, $expected)"
  }

  def mustThrowA[A, T <: Throwable]
      (implicit aTag: WeakTypeTag[A], tTag: WeakTypeTag[T]): Tree = {

    val aTpe = weakTypeOf[A]
    val tTpe = weakTypeOf[T]
    val lhs = unpack
    q"assert_ThrowImpl[$aTpe, $tTpe]($lhs)"
  }

  def tryUnpack[A]: Tree  = {
    val msg = try {
      unpack
      ""
    } catch {
      case e:Throwable => e.getMessage
    }
    q"$msg"
  }

}


