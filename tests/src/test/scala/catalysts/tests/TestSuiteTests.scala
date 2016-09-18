package catalysts
package tests

import catalysts.testkit.{StdTest, StressTestMode, TestModifier, TestNotifications, TestSettings, WellTested}

class StdTestTests extends TestSuite with scalatest.DisableTripleEquals {

  test("StdTest minSuccessful") {
    val min = if (Platform.isJvm) 100 else 10
    assert(generatorDrivenConfig.minSuccessful.value == min)
  }

  test("StdTest maxDiscardedFactor") {
    val max = if (Platform.isJvm) 5.0 else 50.0
    assert(generatorDrivenConfig.maxDiscardedFactor.value == max)
  }

  test("StdTest names") {
    assert(projectKey == "catalysts")
  }

  test("StdTest arbitaryTry") {
    import org.scalacheck.Arbitrary
    import scala.util.Try

    val ti = arbitraryTry(Arbitrary.arbInt)
    assert(ti.isInstanceOf[Arbitrary[Try[Int]]])
    for(i <- 0 until 100) ti.arbitrary.sample
  }

  test("StdTest convertToEqualizer") {
    try {
      val x = convertToEqualizer(1)
      assert(false)
    } catch {
      case e: Throwable => assert(true)
    }
  }
}

class WellTestedTests extends TestSuite with WellTested {

  test("WellTested minSuccessful") {
    val min = if (Platform.isJvm) 10 else 1
    assert(generatorDrivenConfig.minSuccessful.value == min)
  }

  test("WellTested maxDiscardedFactor") {
    val max = if (Platform.isJvm) 5.0 else 50.0
    assert(generatorDrivenConfig.maxDiscardedFactor.value == max)
  }
}

class WellTestedCustomPlatformTests extends TestSuite with WellTested {

 override def minSuccessful: Int =
    if (Platform.isJvm) 50
    else 5

  override def maxDiscardedFactor: Double =
    if (Platform.isJvm) 2.0
    else 20.0

  override def testType: TestModifier =
    if (Platform.isJvm) TestModifier(0.1, 0.5)
    else TestModifier(0.1, 0.5)

  test("WellTested CustomPlatform  minSuccessful") {
    val min = if (Platform.isJvm) 5 else 1
    assert(generatorDrivenConfig.minSuccessful.value == min)
  }

  test("WellTested CustomPlatform maxDiscardedFactor") {
    val max = if (Platform.isJvm) 1.0 else 10.0
    assert(generatorDrivenConfig.maxDiscardedFactor.value == max)
  }
}

trait MySetup extends TestSettings with StdTest with TestNotifications {

  override  def shouldNotify: Boolean = false

  override def minSuccessful: Int =
    if (Platform.isJvm) 50
    else 5

  override def maxDiscardedFactor: Double =
    if (Platform.isJvm) 2.0
    else 20.0

  override def testType: TestModifier =
    if (Platform.isJvm) TestModifier(0.1, 0.5)
    else TestModifier(0.1, 0.5)
}

class MySetupTests extends TestSuite with MySetup {

  test("MySetup  minSuccessful") {
    val min = if (Platform.isJvm) 5 else 1
    assert(generatorDrivenConfig.minSuccessful.value == min)
  }

  test("MySetup maxDiscardedFactor") {
    val max = if (Platform.isJvm) 1.0 else 10.0
    assert(generatorDrivenConfig.maxDiscardedFactor.value == max)
  }
}

class StressTestModeTest extends TestSuite {

  override def getTestMode: TestModifier = StressTestMode.testMode

  test("StressTestMode StdTest minSuccessful") {
    val min = if (Platform.isJvm) 100000 else 1000
    assert(generatorDrivenConfig.minSuccessful.value == min)
  }

  test("StressTestMode StdTest maxDiscardedFactor") {
    val max = if (Platform.isJvm) 500.0 else 500.0
    assert(generatorDrivenConfig.maxDiscardedFactor.value == max)
  }
}

trait MyStressSetup extends TestSettings with StdTest with TestNotifications {

  override def minSuccessful: Int =
    if (Platform.isJvm) 45 else 7

  override def maxDiscardedFactor: Double =
    if (Platform.isJvm) 4.0 else 14.0

  override def testType: TestModifier =
    if (Platform.isJvm) TestModifier(0.4, 0.7)
    else TestModifier(0.3, 0.3)
}

class MyStressTestModeTest extends TestSuite with MyStressSetup {

  override def getTestMode: TestModifier = if (Platform.isJvm) TestModifier(888, 88.0)
    else TestModifier(77, 17.0)

  test("MyStressTestMode minSuccessful") {
    val min =
      if (Platform.isJvm) (45 * 888 * 0.4).toInt
      else (7 * 77 * .3).toInt

    assert(generatorDrivenConfig.minSuccessful.value == min)
  }

  test("MyStressTestMode maxDiscardedFactor") {
    val max =
      if (Platform.isJvm) 4 * 88 * 0.7
      else 14 * 17 * 0.3

    assert(generatorDrivenConfig.maxDiscardedFactor.value == max)
  }
}
 
