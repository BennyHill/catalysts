package catalysts
package lawkit

import catalysts.testkit.TestKit
import org.scalacheck.{Shrink, Arbitrary}

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import macrocompat.bundle

trait LawSpec { self: LawKit with TestKit =>

  def forAllImpl1[T1, R](fun: (T1) => R)(implicit
      arbA : Arbitrary[T1], shrA : Shrink[T1], pretty1: Prettify[T1]): ForAllResult

  def forAllImpl2[T1, T2, R](fun: (T1, T2) => R)(implicit
      arbA : Arbitrary[T1], shrA : Shrink[T1], pretty1: Prettify[T1],
      arbB : Arbitrary[T2], shrB : Shrink[T2], pretty2: Prettify[T2]): ForAllResult

  def forAllImpl3[T1, T2, T3, R](fun: (T1, T2, T3) => R)(implicit
      arbA : Arbitrary[T1], shrA : Shrink[T1], pretty1: Prettify[T1],
      arbB : Arbitrary[T2], shrB : Shrink[T2], pretty2: Prettify[T2],
      arbC : Arbitrary[T3], shrC : Shrink[T3], pretty3: Prettify[T3]): ForAllResult

  def forAllImpl4[T1, T2, T3, T4, R](fun: (T1, T2, T3, T4) => R)(implicit
      arbA : Arbitrary[T1], shrA : Shrink[T1], pretty1: Prettify[T1],
      arbB : Arbitrary[T2], shrB : Shrink[T2], pretty2: Prettify[T2],
      arbC : Arbitrary[T3], shrC : Shrink[T3], pretty3: Prettify[T3],
      arbD : Arbitrary[T4], shrD : Shrink[T4], pretty4: Prettify[T4]): ForAllResult

  def forAllImpl5[T1, T2, T3, T4, T5, R](fun: (T1, T2, T3, T4, T5) => R)(implicit
      arbA : Arbitrary[T1], shrA : Shrink[T1], pretty1: Prettify[T1],
      arbB : Arbitrary[T2], shrB : Shrink[T2], pretty2: Prettify[T2],
      arbC : Arbitrary[T3], shrC : Shrink[T3], pretty3: Prettify[T3],
      arbD : Arbitrary[T4], shrD : Shrink[T4], pretty4: Prettify[T4],
      arbE : Arbitrary[T5], shrE : Shrink[T5], pretty5: Prettify[T5]): ForAllResult

  def forAllImpl6[T1, T2, T3, T4, T5, T6, R](fun: (T1, T2, T3, T4, T5, T6) => R)(implicit
      arbA : Arbitrary[T1], shrA : Shrink[T1], pretty1: Prettify[T1],
      arbB : Arbitrary[T2], shrB : Shrink[T2], pretty2: Prettify[T2],
      arbC : Arbitrary[T3], shrC : Shrink[T3], pretty3: Prettify[T3],
      arbD : Arbitrary[T4], shrD : Shrink[T4], pretty4: Prettify[T4],
      arbE : Arbitrary[T5], shrE : Shrink[T5], pretty5: Prettify[T5],
      arbF : Arbitrary[T6], shrF : Shrink[T6], pretty6: Prettify[T6]): ForAllResult

  def all[T1, R](fun: (T1) => R):  ForAllResult =
    macro LawSpecMacros.forAll1[T1, R]

  def all[T1, T2, R](fun: (T1, T2) => R): ForAllResult =
    macro LawSpecMacros.forAll2[T1, T2, R]

  def all[T1, T2, T3, R](fun: (T1, T2, T3) => R): ForAllResult =
    macro LawSpecMacros.forAll3[T1, T2, T3, R]

  def all[T1, T2, T3,T4, R](fun: (T1, T2, T3, T4) => R): ForAllResult =
    macro LawSpecMacros.forAll4[T1, T2, T3,T4, R]

  def all[T1, T2, T3,T4, T5, R](fun: (T1, T2, T3, T4, T5) => R): ForAllResult =
    macro LawSpecMacros.forAll5[T1, T2, T3,T4, T5, R]

  def all[T1, T2, T3,T4, T5, T6, R](fun: (T1, T2, T3, T4, T5, T6) => R): ForAllResult =
    macro LawSpecMacros.forAll6[T1, T2, T3,T4, T5, T6, R]
}

@bundle
class LawSpecMacros(val c: whitebox.Context) {

  import c.universe._

  def forAll1[T1, R](fun: Tree): Tree =
    q"forAllImpl1($fun)"

  def forAll2[T1, T2, R](fun: Tree): Tree =
    q"forAllImpl2($fun)"

  def forAll3[T1, T2, T3, R](fun: Tree): Tree =
    q"forAllImpl3($fun)"

  def forAll4[T1, T2, T3, T4, R](fun: Tree): Tree =
    q"forAllImpl4($fun)"

  def forAll5[T1, T2, T3, T4, T5, R](fun: Tree): Tree =
    q"forAllImpl5($fun)"

  def forAll6[T1, T2, T3, T4, T5, T6, R](fun: Tree): Tree =
    q"forAllImpl6($fun)"
}
