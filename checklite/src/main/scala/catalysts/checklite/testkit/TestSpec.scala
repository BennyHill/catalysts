package catalysts
package checklite
package testkit

import catalysts.testkit.{TestSpec => BaseTestSpec, Equal, Show}
import catalysts.lawkit.{LawSpec => BaseLawSpec}
import org.scalacheck._
import org.scalacheck.Prop.forAll

import scala.reflect.ClassTag

trait TestSpec extends CheckLite with BaseTestSpec with BaseLawSpec with TestKit {

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

  def forAllImpl1[T1, R] (fun: (T1) => R)(implicit
      arbA : Arbitrary[T1], shrA : Shrink[T1], pretty1: Prettify[T1]): ForAllResult =
    forAll(fun.asInstanceOf[(T1) => Prop])

  def forAllImpl2[T1, T2, R](fun: (T1, T2) => R)(implicit
      arbA : Arbitrary[T1], shrA : Shrink[T1], pretty1: Prettify[T1],
      arbB : Arbitrary[T2], shrB : Shrink[T2], pretty2: Prettify[T2]): ForAllResult =
    forAll(fun.asInstanceOf[(T1, T2) => Prop])

  def forAllImpl3[T1, T2, T3, R](fun: (T1, T2, T3) => R)(implicit
      arbA : Arbitrary[T1], shrA : Shrink[T1], pretty1: Prettify[T1],
      arbB : Arbitrary[T2], shrB : Shrink[T2], pretty2: Prettify[T2],
      arbC : Arbitrary[T3], shrC : Shrink[T3], pretty3: Prettify[T3]): ForAllResult =
    forAll(fun.asInstanceOf[(T1, T2, T3) => Prop])

  def forAllImpl4[T1, T2, T3, T4, R](fun: (T1, T2, T3, T4) => R)(implicit
      arbA : Arbitrary[T1], shrA : Shrink[T1], pretty1: Prettify[T1],
      arbB : Arbitrary[T2], shrB : Shrink[T2], pretty2: Prettify[T2],
      arbC : Arbitrary[T3], shrC : Shrink[T3], pretty3: Prettify[T3],
      arbD : Arbitrary[T4], shrD : Shrink[T4], pretty4: Prettify[T4]): ForAllResult =
    forAll(fun.asInstanceOf[(T1, T2, T3, T4) => Prop])

  def forAllImpl5[T1, T2, T3, T4, T5, R](fun: (T1, T2, T3, T4, T5) => R)(implicit
      arbA : Arbitrary[T1], shrA : Shrink[T1], pretty1: Prettify[T1],
      arbB : Arbitrary[T2], shrB : Shrink[T2], pretty2: Prettify[T2],
      arbC : Arbitrary[T3], shrC : Shrink[T3], pretty3: Prettify[T3],
      arbD : Arbitrary[T4], shrD : Shrink[T4], pretty4: Prettify[T4],
      arbE : Arbitrary[T5], shrE : Shrink[T5], pretty5: Prettify[T5]): ForAllResult =
    forAll(fun.asInstanceOf[(T1, T2, T3, T4, T5) => Prop])

  def forAllImpl6[T1, T2, T3, T4, T5, T6, R](fun: (T1, T2, T3, T4, T5, T6) => R)(implicit
      arbA : Arbitrary[T1], shrA : Shrink[T1], pretty1: Prettify[T1],
      arbB : Arbitrary[T2], shrB : Shrink[T2], pretty2: Prettify[T2],
      arbC : Arbitrary[T3], shrC : Shrink[T3], pretty3: Prettify[T3],
      arbD : Arbitrary[T4], shrD : Shrink[T4], pretty4: Prettify[T4],
      arbE : Arbitrary[T5], shrE : Shrink[T5], pretty5: Prettify[T5],
      arbF : Arbitrary[T6], shrF : Shrink[T6], pretty6: Prettify[T6]): ForAllResult =
    forAll(fun.asInstanceOf[(T1, T2, T3, T4, T5, T6) => Prop])
}
