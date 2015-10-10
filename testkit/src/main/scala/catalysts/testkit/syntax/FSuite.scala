package catalysts
package testkit
package syntax

trait FSuite[Tk <: TestKit] {self : TestSpec[Tk] => 
  def test(s: String)(a: => Any) = block(s)(a)
  def suite(s: String)(a: => Any) = nest(s)(a)
}

trait FSuiteMatchers [Tk <: TestKit] {self : TestSpec[Tk] =>

  def assertEquals[A](actual: => A, expected: => A) = assert_==(actual, expected)

  def assertEquals[A](msg:String, actual: => A, expected: => A) = assert_==(msg, actual, expected)
     
  def assertFalse(actual: => Boolean) = assert_==[Boolean](actual, false)

  def assertTrue(actual: => Boolean) = assert_==[Boolean](actual, true)

  def assertTrue() = assert_==[Boolean](true, true)

  def assertFalse() = assert_==[Boolean](true, false)

  def assertEquals(v1: Double, v2: Double, delta:Double) =
    assertTrue(Math.abs(v1 - v2) < delta)

  def assertTypedEquals[A](actual: A, expected: A) = assert_==(actual, expected)

  def assertTypedSame[A <: AnyRef](actual: A, expected: A) = assertTrue(actual eq expected)
}
