package catalysts
package speclite
package testkit

import catalysts.testkit.{Equal, Show, TestSpec => BaseTestSpec}
import scala.reflect.ClassTag

trait TestSpec extends SpecLite with BaseTestSpec with TestKit {

  def assertEqEqImpl[A](actual: => A, expected: => A): AssertResult = {
    actual must_== expected
    ()
  }

  def assertMsgEqEqImpl[A](msg: String, actual: => A, expected: => A): AssertResult = {
     actual must_== expected
    ()
  }

  def  assertEqEqEqImpl[A](actual: => A, expected: => A)
      (implicit show: Show[A], equal: Equal[A]): AssertResult = {
    actual must_=== (expected)
    ()
  }

  def nest(s: String)(a: => Any): Unit = {
    s should a
  }

  def block(s: String)(a: => Any): Unit = {
    s.in(a)
  }

  def  assert_ThrowImpl[A, T <: Throwable](actual: => A)(implicit m: ClassTag[T]): ExceptionResult = {
    actual.mustThrowA[T]
    ()
  }
}
