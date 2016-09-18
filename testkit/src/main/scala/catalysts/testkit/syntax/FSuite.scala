package catalysts
package testkit
package syntax


trait FSuite { self : TestSpec =>

  def test(s: String)(a: => Any) = block(s)(a)

  def suite(s: String)(a: => Any) = nest(s)(a)
}

trait FSuiteMatchers { self : TestKit =>

  def assertEquals[A](actual: => A, expected: => A): AssertResult =
    macro FSuiteMatchersMacros.assertEq[A]

  def assertEquals[A](msg:String, actual: => A, expected: => A): AssertResult =
    macro FSuiteMatchersMacros.assertMsgEq[A]

  def assertFalse(actual: => Boolean): AssertResult =
    macro FSuiteMatchersMacros.assertAcFalse

  def assertTrue(actual: => Boolean): AssertResult =
    macro FSuiteMatchersMacros.assertAcTrue

  def assertTrue(): AssertResult =
    macro FSuiteMatchersMacros.assertTrue

  def assertFalse(): AssertResult =
    macro FSuiteMatchersMacros.assertFalse

  def assertEquals(v1: Double, v2: Double, delta:Double): AssertResult =
    macro FSuiteMatchersMacros.assertEquals

  def assertTypedEquals[A](actual: A, expected: A): AssertResult =
    macro FSuiteMatchersMacros.assertTypedEquals[A]

  def assertTypedSame[A <: AnyRef](actual: A, expected: A): AssertResult =
    macro FSuiteMatchersMacros.assertTypedSame[A]
}

import scala.reflect.macros.whitebox
import macrocompat.bundle

@bundle
class FSuiteMatchersMacros(val c: whitebox.Context)  {
  import c.universe._

  def assertEq[A](actual: Tree, expected: Tree): Tree =
    q"assertEqEqImpl($actual , $expected)"

  def assertMsgEq[A](msg: Tree, actual: Tree, expected: Tree): Tree =
    q"assertMsgEqEqImpl($msg, $actual , $expected)"

  def assertAcFalse(actual: Tree): Tree =
    q"assertEqEqImpl($actual , false)"

  def assertAcTrue(actual: Tree): Tree =
    q"assertEqEqImpl($actual , true)"

  def assertTrue(): Tree =
    q"assertEqEqImpl(true , true)"

  def assertFalse(): Tree =
    q"assertEqEqImpl(true , false)"

  def assertEquals(v1: Tree, v2: Tree, delta: Tree): Tree =
    q"assertEqEqImpl(Math.abs($v1 - $v2) < $delta, true)"

  def assertTypedEquals[A](actual: Tree, expected: Tree): Tree =
    q"assertEqEqImpl($actual , $expected)"

  def assertTypedSame[A <: AnyRef](actual: Tree, expected: Tree): Tree =
    q"assertEqEqImpl($actual eq $expected, true)"
}
