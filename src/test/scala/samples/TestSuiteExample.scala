/*
 * Created 2019 by Dylan Kessler
 */

package samples

import org.scalatest.{FunSuite, Matchers}

class TestSuiteExample extends FunSuite with Matchers {
  val nums: List[Int] = (1 to 20).toList
  val threeMults = nums.filter(_ % 3 == 0)

  test("filtering a list") {
    val filtered = nums.filter(_ > 15)
    assert(filtered === Seq(16,17,18,19,20))
  }

  test("summing a list") {
    nums.sum should be (210)
  }

  test("try something else")(pending)


}
