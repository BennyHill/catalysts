package catalysts
package specbase

import catalysts.testkit.{Equal, Show}
import scala.reflect.ClassTag

trait SpecBase[P,PS] {


  private[catalysts] var context: String = ""

  def check(x: => Boolean): Boolean = x must_== true

  def fail(msg: String): Nothing = throw new AssertionError(msg)

  abstract class SpecBaseStringOps(s: String) {

    def should[A](a: => Any): Unit = {
      val saved = context
      context = s
      try {
        val r = a
      } finally {
        context = saved
      }
    }

    def in[A](a: => A)(implicit ev: (=> A) => P): Unit
  }

  implicit class AnyOps2[A](actual: => A) {

    def must_===(expected: A)(implicit s: Show[A], e: Equal[A]): Boolean = {
      val act = actual
      def test = e.eqv(expected, act)
      def koMessage = "%s !== %s".format(s.show(act), s.show(expected))

      if (!test)
        fail(koMessage)
      else true
    }

    def must_==(expected: A): Boolean = {
      val act = actual
      def test = expected == act
      def koMessage = "Expected (%s) to equal (%s)".format(act, expected)
      if (!test)
        fail(koMessage)
      else true
    }

    def mustMatch(f: PartialFunction[A, Boolean]): Boolean = {
      val act = actual
      def test = f.isDefinedAt(act) && f(act)
      def koMessage = "%s does not satisfy partial function".format(act)
      if (!test)
        fail(koMessage)
      else true
    }

    def and[B](b: => B): B = {
      actual
      b
    }

    def mustBe_<(x: Int)(implicit ev: A <:< Int): Boolean = {
      val act = actual
      def test = ev(act) < x
      def koMessage = "%s <! %s".format(actual, x)
      if (!test)
        fail(koMessage)
      else true
    }

    def mustThrowA[T <: Throwable](implicit man: ClassTag[T]): Boolean = {
      val erasedClass = man.runtimeClass
      val failed: Boolean  =
        try {
          actual
          true
        } catch {
          case ex: Throwable =>
            if (!erasedClass.isInstance(ex))
              fail("wrong exception thrown, expected: " + erasedClass + " got: " + ex)
            else false
        }
      if (failed) fail("no exception thrown, expected " + erasedClass)
      else true
    }
  }

  implicit class AnyRefOps[A <: AnyRef](actual: => A) {

    def mustBeTheSameAs(expected: A): Boolean = {
      val act = actual
      def test = expected eq act
      def koMessage = "Expected (%s) to be the same as (%s)".format(act, expected)
      if (!test)
        fail(koMessage)
      else true
    }
  }

  implicit def propToProp(p: => P): P = p
  implicit def anyToProp(u: => Any): P = booleanToProp({u; true})
  implicit def booleanToProp(b: => Boolean): P
}
