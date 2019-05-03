package catalysts
package testkit

import scala.reflect.ClassTag
import scala.language.experimental.macros
import scala.reflect.macros.blackbox
import macrocompat.bundle

trait TestSpec { self: TestKit =>

  def assertEqEqImpl[A](actual: => A, expected: => A): AssertResult

  def assertMsgEqEqImpl[A](msg: String, actual: => A, expected: => A): AssertResult

  def assertEqEqEqImpl[A](actual: => A, expected: => A)
                         (implicit show: Show[A], equal: Equal[A]): AssertResult

  def assert_ThrowImpl[A, T <: Throwable](actual: => A)(implicit m: ClassTag[T]): ExceptionResult

  def block(s: String)(a: => Any): TestBlock

  def nest(s: String)(a: => Any): TestNest

  def assert_==[A](actual: => A, expected: => A): AssertResult =
    macro TestSpecMacros.assertEqEq[A]

  def assert_==[A](msg: String, actual: => A, expected: => A): AssertResult =
    macro TestSpecMacros.assertMsgEqEq[A]

  def assert_===[A](actual: => A, expected: => A): AssertResult =
    macro TestSpecMacros.assertEqEqEq[A]

  def assert_Throw[A, T <: Throwable](actual: => A): ExceptionResult =
    macro TestSpecMacros.assert_Throw[A, T]
}

@bundle
class TestSpecMacros(val c: blackbox.Context)  {
  import c.universe._

  def assertEqEq[A](actual: Tree, expected: Tree): Tree =
    q"assertEqEqImpl($actual , $expected)"

  def assertMsgEqEq[A](msg: Tree, actual: Tree, expected: Tree): Tree =
    q"assertMsgEqEqImpl($msg, $actual , $expected)"

  def assertEqEqEq[A](actual: Tree, expected: Tree): Tree =
    q"assertEqEqEqImpl($actual , $expected)"

  def assert_Throw[A, T <: Throwable](actual: Tree)
                                     (implicit aTag: WeakTypeTag[A], tTag: WeakTypeTag[T]) : Tree = {

    val aTpe = weakTypeOf[A]
    val tTpe = weakTypeOf[T]
    q"assert_ThrowImpl[$aTpe, $tTpe]($actual)"
  }
}


trait Show[T] {
  def show(f: T): String
}

object Show {
  /** creates an instance of [[Show]] using the provided function */
  def show[A](f: A => String): Show[A] = new Show[A] {
    def show(a: A): String = f(a)
  }

  /** creates an instance of [[Show]] using object toString */
  def fromToString[A]: Show[A] = new Show[A] {
    def show(a: A): String = a.toString
  }
}

trait Equal[A] extends Any { self =>

  /**
   * Returns `true` if `x` and `y` are equivalent, `false` otherwise.
   */
  def eqv(x: A, y: A): Boolean

}
object Equal  {

  /**
   * Create an `Eq` instance from an `eqv` implementation.
   */
  def instance[A](f: (A, A) => Boolean): Equal[A] =
    new Equal[A] {
      def eqv(x: A, y: A) = f(x, y)
    }
}
