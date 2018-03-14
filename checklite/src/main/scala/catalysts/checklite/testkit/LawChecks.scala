package catalysts
package checklite
package testkit

import catalysts.lawkit.{LawChecks => BaseLawChecks}
import org.typelevel.discipline.Laws

trait LawChecks extends BaseLawChecks   {
  self: CheckLite with TestSpec with TestKit =>

  def checkAllLaws(name: String, ruleSet: Laws#RuleSet): Structure =
    checkAll(name, ruleSet)

  def checkAll(name: String, ruleSet: Laws#RuleSet) = {
    for ((id, prop) ← ruleSet.all.properties)
      block(name + "." + id) {
        property(name + ":" + id) = prop
      }
  }
}
