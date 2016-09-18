package catalysts
package speclite

import catalysts.specbase.SpecBaseTests
import catalysts.speclite.Pretty.Params
import catalysts.speclite.Prop.Arg
//import catalysts.speclite.SpecLiteTest
import catalysts.testkit._


class SpecliteSpecBaseTests extends Suite with SpecBaseTests[Prop, Properties]
class SpecliteFSpecTests extends Suite with FSpecTests
class SpecliteFSuiteTests extends Suite with FSuiteTests
class SpecliteJUnitTests extends Suite with JUnitTests
class SpecliteTestSpecTests extends Suite with TestSpecTests
class SpeclitetWSpecTests extends Suite with WSpecTests

class SpecliteCoreTests extends Suite {

  import SpecLiteTest._
  val undecided = Prop.undecided
  val falsified = Prop.falsified
  val passed = Prop.passed
  val proved = Prop.proved
  val exc = Prop.exception

  //testing error condition
  val i = new InStringOps(null).apply(null)

  val res1 = Result(Passed, 1, 1)
  res1.passed must_== true

  val res2 = Result(Proved(List()), 1, 1)
  res1.passed must_== true

  val res2a = Result(Exhausted, 1, 1)
  res2a.passed must_== false

  val res3 = Prop.Result(Prop.Proof)
  val res4 = Prop.Result(Prop.True)
  res4.&&(res3)
  res3.&&(res4)
  res4.&&(res4)

  val labels = Set("dd")

  val args = Prop.Arg[Any]("label", "arg", 1, "orig",
                     Pretty.pretty("arg"), Pretty.pretty("orig"))
  val p4 = Pretty.prettyArgs(Seq[Arg[Any]]())
  val p5 = Pretty.prettyArgs(Seq(args))

  val pi1:Pretty = Result(Passed, 1, 1)
  pi1.apply(Params(0))

  val pi2: Pretty = Result(Failed(List(args), labels), 1, 1)
  pi2.apply(Params(0))

  val pi3: Pretty = Result(Failed(List(args), Set()), 1, 1)
  pi3.apply(Params(0))

  val pi4: Pretty = Result(PropException(List(), new Error,labels), 1, 1)
  pi4.apply(Params(0))

  val pi5: Pretty = Result(Exhausted, 1, 1)
  pi5.apply(Params(0))


  //def slaveRunner(args: Array[String], remoteArgs: Array[String],
  //                loader: ClassLoader, send: String => Unit): Runner =

  def badParamToResult(f: Gen.Parameters): Prop.Result =  throw new Error

  val prr: Prop = Prop.apply(badParamToResult: Gen.Parameters => Prop.Result)
  prr(Gen.Parameters.default)

  Prop.all(Prop(1 == 1))

  val pEmpty = Seq[Prop]()
  pEmpty.isEmpty must_== true
  Prop.all(pEmpty)

  val ps1 = new Properties("MyProps")
  ps1.apply(Gen.Parameters.default)

  def sender(s: String): Unit = ()
  val spf = new SpecLiteFramework
  val runner1 = spf.slaveRunner(Array[String](), Array[String](),
    null.asInstanceOf[ClassLoader], sender )
  runner1.done




  val e = Prop.Exception(new Throwable)
  assert_==(e.equals(e), true)
  assert_==(e.equals(1), false)

  val myParams = new Parameters.Default {
    override val maxDiscardRatio: Float = -8
  }

  {
    SpecLiteTest.check(myParams, proved)
  }.mustThrowA[IllegalArgumentException]

  val myParams2 = new Parameters.Default {
    override val maxDiscardRatio: Float = -8
  }

  {
    SpecLiteTest.check(myParams, proved)
  }.mustThrowA[IllegalArgumentException]

  SpecLiteTest.check(Prop.Undecided) {
    _.
      withWorkers(4)
  }.mustThrowA[AssertionError]

  def err(): Int = throw new Error

  SpecLiteTest.check(Parameters.default, Prop.Undecided)
  SpecLiteTest.check(Parameters.default, Prop.True)
  SpecLiteTest.check(Parameters.default, Prop(1 == 2))
  SpecLiteTest.check(Parameters.default, prr)
  SpecLiteTest.check(Parameters.default, ps1)
  SpecLiteTest.check(Parameters.default, pEmpty)

  ps1.property.update("Little", Prop(1 == 1))
  val cps = SpecLiteTest.checkProperties(Parameters.default, ps1)


  val parm1 = catalysts.speclite.Gen.Parameters.default
  parm1.size  must_== 100

  val parm2 = catalysts.speclite.Gen.Parameters.default.withSize(99)
  parm2.size  must_== 99



 // Pretty
  import Pretty._
  val pt0 = prettyTime(0)
  val ptM = prettyTime(1000000)

  val p = Pretty.pretty(1)


 // val pThrow = prettyThrowable(new Error(""))

  val tcb1 = new TestCallback {}
  tcb1.onPropEval("S", 1, 1, 1) must_== (())
  tcb1.onTestResult("S",Result(Passed, 1, 1) )  must_== (())

  val tcb2 =  tcb1.chain(new TestCallback {})
  tcb2.onPropEval("S2", 1, 1, 1) must_== (())
  tcb2.onTestResult("S",Result(Passed, 1, 1) )  must_== (())

  block("CmdLineParser Tests") {

    import scala.language.reflectiveCalls
    cmdLineParser.parseParams(Array[String]("-maxSize", "5", "-maxDiscardRatio", "5.0", "-minSuccessfulTests", "33", "-workers", "1", "-verbosity", "1"))

    cmdLineParser.parseParams(Array[String]("-maxDiscardRatio", "Hello", "-verbosity", "", "-minSuccessfulTests", "Hello"))

    cmdLineParser.printHelp()


    trait MyParameters { //extends SpecLiteTest.Parameters{

      val myFlag: Boolean
      val myString: String

      def withMyString(s: String): MyParameters = new myCp(myFlag,s)

      def withMyFlag(flag: Boolean): MyParameters = new myCp(flag, myString)

      class myCp(val myFlag: Boolean, val myString: String) extends MyParameters
    }

    object MyDefault extends MyParameters {
      val myString: String = "S"
      val myFlag: Boolean = false
    }

    val c = new CmdLineParser {

      object OptMyString extends StrOpt {
        val default = "S"
        val names = Set("myString", "x")
        val help = "Is what you need if you are reading this"
      }
      object OptMyFlag extends Flag {
        val default = ()
        val names = Set("myFlag", "f")
        val help = "Is what you need if you are reading this"
      }
      override val opts: Set[Opt[_]] = Set[Opt[_]](OptMyString, OptMyFlag)

      def parseParams(args: Array[String]): Option[MyParameters] = parseArgs(args) {
        optMap => MyDefault
          .withMyString(optMap(OptMyString): String)
          .withMyFlag(optMap(OptMyFlag): Boolean)
      }
    }


    c.parseParams(Array[String]("-myString", "XX", "-myFlag"))
    c.parseParams(Array[String]("", ""))
  }


}
