/*
 * Created 2019 by Dylan Kessler
 */

package samples

import org.junit.Assert._
import org.junit._

//@Test
class AppTest {

    //@Test
    def testOK() = assertTrue(true)

    //@Test
    //def testKO() = assertTrue(false)

    @Test
    def testArraySubstitution(): Unit = {
        // single array of 3 items
        val array1:Array[String] = Array[String]("a","b","c")
        //array1.map(item => println(item))
        // double array of 3 sub-arrays, each with 2 items
        val doubleArray = Array.ofDim[String](3, 2)
        doubleArray(0) = Array[String]("field1","field01")
        doubleArray(1) = Array[String]("field1","field11")
        doubleArray(2) = Array[String]("field1","field21")
        // we want to replace the first filed of each sub-array with the equivalent indexed item from array1
        /**
          * second array =
          * firstarray map
          * (subarray goes to
          * subarray's first element replaced with equivalent element from
          * array1
          */
        for((row,index) <- doubleArray.zipWithIndex) {
            row(0) = array1(index)
        }
        assertTrue(true)
    }
}


