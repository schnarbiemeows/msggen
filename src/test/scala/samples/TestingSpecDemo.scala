/*
 * Created 2019 by Dylan Kessler
 */

package samples

import org.scalacheck.Gen
import org.scalatest._
import org.scalatest.prop.PropertyChecks

class TestingSpecDemo extends FunSpec with Matchers with PropertyChecks {
  describe("property checks") {
    they("should all be positive") {
      forAll { (i: Int) =>
        whenever(i != Int.MinValue) {
          i.abs should be >=0
        }
      }
    }
  }

  val validChars = ('a' to 'z') ++ ( 'A' to 'Z') ++ ('0' to '9')
  val char = Gen.oneOf(validChars)
  val strGen = for {
    n <- Gen.choose(0,30) // 0 to 30 length string
    seqChars <- Gen.listOfN(n, char)
  } yield seqChars.mkString

  describe("property checks and generators") {
    they("should test .reverse.reverse on any string gives back original") {
      forAll(strGen) { str =>
        println(str)
        str.reverse.reverse should be (str)
      }
    }
  }


}
