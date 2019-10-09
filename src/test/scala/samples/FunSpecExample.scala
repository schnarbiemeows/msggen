/*
 * Created 2019 by Dylan Kessler
 */

package samples

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest._

import scala.util.Try

/**
  * this is Behavioral driven design testing suite
  */
class FunSpecExample extends FunSpec with Matchers with MockFactory {

  val nums: List[Int] = (1 to 20).toList
  val threeMults = nums.filter(_ % 3 == 0)



  describe ("various mathcers") {
    describe ("on a list of numbers") {
      they("should allow a wide and varied language for matching"){
        val x = 10*2

        x should be (20)

        threeMults should have size (6)
        threeMults should contain allOf(3,6,12,15)
        threeMults should not contain (10)
        threeMults should be (Vector(3,6,9,12,15,18))
        threeMults should be (sorted)
        all(threeMults) should be > (0)
        atLeast(3, threeMults) should be > (10)
      }
    }
    describe("on a case class example") {
      they("should allow easy field checking") {
        case class Person(first:String, last:String, age:Int)
        val p1 = Person("Harry","Potter",34)
        p1 should have(
          'first ("Harry"),
          'last ("Potter"),
          'age (34)
        )
      }
    }
  }

  describe("Handling exceptions") {
    they("should expect and intercept exceptions") {
      an [IllegalArgumentException] should be thrownBy {
        require(1 == 2, "one equals two?")
      }

      val ae = intercept[ArithmeticException] (1/0)
      ae.getMessage should be ("/ by zero")
    }
  }

  // checking floating point values within a specific tolerance
  describe("floating point values") {
    they("should be matched within a tolerance") {
      val sqrt = math.sqrt(2.0)
      sqrt should be(1.41421356 +- 1e-6)
      sqrt should (be > 1.41421355 and be < 1.41421357)
    }
  }

  /**
    * unit tests should work without external resources, i.e, you should still be able to run the
    * even without internet connection.
    * this is what makes mocks and stubs handy, is for making unit tests work without external resources
     */


  /**
    * different mocks:
    * scalamock
    * easyMock
    * mockito
    *
    */

  case class Weather(temp:Double, precip:Double, pressure:Double)

  trait WeatherService {
    def getWeather(code: String):Weather
    def operational():Boolean
    /*def anothermethod():Boolean*/
  }

  def lookupWeather(service: WeatherService, code:String) : Option[Weather] = {
    if(!service.operational()) None
    else Some(service.getWeather(code))
  }

  describe ("retrieving the weather from the weather service") {
    it ("should call getWeather only if operational is true"){
      val mockWS = mock[WeatherService]

      /**
        * below defines the behavior of the mock service:
        * if no parameters are passed in, you have to use "() => Boolean
        * expects(stuff) --> stuff are the paramters that will be passed in
        * NOTE: the mock does NOT have to define the behavior for ALL of the
        * methods of a class(or trait), BUT, every method of the class whose
        * behavior is defined below OR that gets called during the course of the test
        * (like !serive.operational() above) MUST be called during the course of the test,
        * or the test will fail
        * exampple: the anothermethod() method is never defined below or gets called during
        * the course of the test, but that is OK
        */
      (mockWS.operational _: () => Boolean).expects().returning(true)
      (mockWS.getWeather _).expects("PDX").returning(Weather(55.0,0.0,1012.0))

      val weather = lookupWeather(mockWS, "PDX")

      val tolerance = 1e-6
      weather should be (defined)
      weather.get.precip should be (0.0 +- tolerance)
      weather.get.pressure should be (1012.0 +- tolerance)
      weather.get.temp should be (55.0 +- tolerance)
    }
    it ("should not call getWeather if the service is not operational") {
      val mockWS = mock[WeatherService]
      (mockWS.operational _: () => Boolean).expects().
        throwing(new IllegalStateException("network failure"))
      val ex = intercept[IllegalStateException] {
        lookupWeather(mockWS,"PDX")
      }
      ex.getMessage should be ("network failure")
    }
    it ("should not call getWeather if operational throws an exception")(pending)
    it ("should call getWeather with different codes when necessary"){
      /**
        * with mocks, you script up what you expect
        * with stubs, the emphasis is on verifying what you get back from a service
        */
      val stubWS = stub[WeatherService]
      (stubWS.operational _: () => Boolean).when().returning(true)
      (stubWS.getWeather _).when("PDX").returning(Weather(55.0,0.0,1012.0))
      (stubWS.getWeather _).when("SFO").returning(Weather(65.0,0.3,1008.0))

      val weather1 = lookupWeather(stubWS, "PDX")
      val weather2 = lookupWeather(stubWS, "SFO")

      // verify what was called
      (stubWS.operational _: () => Boolean).verify().twice()
      (stubWS.getWeather _).verify("PDX")
      (stubWS.getWeather _).verify("SFO")
      println("here")
    }
  }

  /**
    * Fakes - used to simulate an entire system, like say a DB connection
    * like, the trait and class below, the itemMap is just used as a pretend DB store
    * that you would call this thing instead of calling an actual database
     */


  trait DBAccess {
    def save[T](item:T):String
    def load[T](id:String):Option[T]
  }

  class FakeDBAccess extends DBAccess {
    private[this] var itemMap = Map.empty[String, Any]

    def save[T](item:T):String = {
      val uuid = UUID.randomUUID().toString
      itemMap = itemMap + (uuid -> item)
      uuid
    }

    def load[T](id:String):Option[T] = {
      Try(itemMap(id).asInstanceOf[T]).toOption
    }
  }

  case class Person(name:String, age:Int)
  val fake = new FakeDBAccess
  val uuid = fake.save(Person("Sally",23))
  fake.load(uuid)

  // Scalacheck - can generate random testing data


}
