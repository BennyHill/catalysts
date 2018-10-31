// Based on work from Rickard Nilsson at http://www.scalacheck.org

package catalysts
package speclite

import scala.language.{reflectiveCalls}
import Prop.Arg

sealed trait Pretty extends Serializable {

  def apply(prms: Pretty.Params): String
}

object Pretty {

  case class Params(verbosity: Int)

  val defaultParams = Params(0)

  def apply(f: Params => String): Pretty = new Pretty { def apply(p: Params) = f(p) }

  def pretty[T](t: T, prms: Params)(implicit ev: T => Pretty): String = t(prms)

  def pretty[T](t: T)(implicit ev: T => Pretty): String = t(defaultParams)

  implicit def strBreak(s1: String) = new {
    def /(s2: String) = if(s2 == "") s1 else s1+"\n"+s2
  }

  implicit def prettyAny(t: Any) = Pretty { p => t.toString }

  def prettyArgs(args: Seq[Arg[Any]]): Pretty = Pretty { prms =>
    if(args.isEmpty) "" else {
      for((a,i) <- args.zipWithIndex) yield {
        val l = "> "+(if(a.label == "") "ARG_"+i else a.label)
        val s =
          if(a.shrinks == 0) ""
          else "\n"+l+"_ORIGINAL: "+a.prettyOrigArg(prms)
        l+": "+a.prettyArg(prms)+""+s
      }
    }.mkString("\n")
  }

  implicit def prettyTestRes(res: SpecLiteTest.Result) =  {
    Pretty { prms =>
      def labels(ls: collection.immutable.Set[String]) =
        if(ls.isEmpty) ""
        else "> Labels of failing property: " / ls.mkString("\n")
      val s = res.status match {
        case SpecLiteTest.Proved(args) => "" //"OK, proved property."/prettyArgs(args)(prms)
        case SpecLiteTest.Passed => "OK, passed "+res.succeeded+" tests."
        case SpecLiteTest.Failed(args, l) =>
          "Falsified after "+res.succeeded+" passed tests."/labels(l)/prettyArgs(args)(prms)
        case SpecLiteTest.Exhausted =>
          "Gave up after only "+res.succeeded+" passed tests. " +
            res.discarded+" tests were discarded."
       case SpecLiteTest.PropException(args,e,l) => s"failed: ${e.getMessage}"
        //"Exception raised on property evaluation."/labels(l)/prettyArgs(args)(prms)/
        //RED + "> Exception: " + e.getMessage // pretty(e,prms)
      }
      val t = if(prms.verbosity <= 1) "" else "Elapsed time: "+prettyTime(res.time)
      s/t //pretty(res.freqMap,prms)
    }
  }

  def prettyTime(millis: Long): String = {
    val min = millis/(60*1000)
    val sec = (millis-(60*1000*min)) / 1000d
    if(min <= 0) "%.3f sec ".format(sec)
    else "%d min %.3f sec ".format(min, sec)
  }
}
